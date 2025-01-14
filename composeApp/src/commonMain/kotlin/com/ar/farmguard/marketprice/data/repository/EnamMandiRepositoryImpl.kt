package com.ar.farmguard.marketprice.data.repository

import com.ar.farmguard.app.utils.IMG_BASE_URL_CROPS
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.core.presentation.parseDate
import com.ar.farmguard.marketprice.domain.model.CommodityState
import com.ar.farmguard.marketprice.domain.model.remote.TradeData
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest
import com.ar.farmguard.marketprice.domain.network.EnamMandiApi
import com.ar.farmguard.marketprice.domain.repository.EnamMandiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlin.math.round

class EnamMandiRepositoryImpl(
    private val api: EnamMandiApi
): EnamMandiRepository {

    override suspend fun getTradeList(tradeDataRequest: TradeDataRequest): Result< List<CommodityState>?, DataError.Remote> {
        return api.getTradeList(tradeDataRequest).map {
            if(it.status == 200L){
                groupTradeDataByCommodity(it.data, tradeDataRequest.apmcName)
            }else{
                null
            }
        }
    }

    private suspend fun groupTradeDataByCommodity(tradeDataList: List<TradeData>, apmcName: String): List<CommodityState> = withContext(Dispatchers.IO) {
        val groupedData = tradeDataList
            .groupBy { it.commodity }
            .map { (commodity, trades) ->
                async {
                    CommodityState(
                        apmc = apmcName,
                        commodity = commodity,
                        image = "$IMG_BASE_URL_CROPS/$commodity.jpg",
                        tradeData = trades.sortedByDescending {
                            parseDate(it.createdAt)
                        }
                    )
                }
            }

        val newData = groupedData.awaitAll().map { commodityState ->
            async {

                val prices = commodityState.tradeData.mapNotNull {
                    it.maxPrice.toDoubleOrNull()
                }

                val priceThreads = calculatePriceChange(prices)

                if(priceThreads.isNotEmpty()){

                    val firstThread = priceThreads.first()

                    commodityState.copy(
                        priceColor = if (firstThread > 0 ) 0xFF1E8805.toInt() else 0xFFB00020.toInt(),
                        currentPriceThread = if (firstThread > 0) "+${firstThread}%" else "${firstThread}%",
                        priceThreads = priceThreads,
                    )
                }
                else{
                    commodityState.copy(
                        priceColor = 0xFF1E8805.toInt() ,
                        currentPriceThread = "+00.00%",
                        priceThreads = priceThreads
                    )
                }

            }
        }

        return@withContext newData.awaitAll()
    }

    private suspend inline fun calculatePriceChange(prices: List<Double>): List<Float> = withContext(Dispatchers.IO) {
        val previousPrices = prices.dropLast(1)
        val currentPrices = prices.drop(1)

        val list = previousPrices.zip(currentPrices) { previous, current ->
            async {
                val priceChange = current - previous
                val percentageChange = if (previous != 0.0) (priceChange / previous) * 100 else 0.0

                (round(percentageChange * 100) / 100.0).toFloat()
            }
        }

        return@withContext list.awaitAll()
    }

}