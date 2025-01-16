package com.ar.farmguard.marketprice.data.repository

import com.ar.farmguard.app.utils.IMG_BASE_URL_CROPS
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.core.presentation.parseDate
import com.ar.farmguard.marketprice.domain.model.remote.EnamApmc
import com.ar.farmguard.marketprice.domain.model.remote.EnamState
import com.ar.farmguard.marketprice.domain.model.state.CommodityState
import com.ar.farmguard.marketprice.domain.model.remote.CommodityTransaction
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest
import com.ar.farmguard.marketprice.domain.model.state.toTradeReport
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
        val liveTrade = getLiveTradeList(tradeDataRequest)
        return api.getTradeList(tradeDataRequest).map {
            if(it.status == 200L){
                groupTradeDataByCommodity(it.data, tradeDataRequest.apmcName, liveTrade)
            }else{
                null
            }
        }
    }

    override suspend fun getStateData(): Result<EnamState, DataError.Remote> {
        return api.getStateData()
    }

    override suspend fun getApmcData(stateId: String): Result<EnamApmc, DataError.Remote> {
        return api.getApmcData(stateId)
    }

    override suspend fun getLiveTradeList(tradeDataRequest: TradeDataRequest): Result<List<CommodityTransaction>, DataError> {
        return api.getLiveTradeList(tradeDataRequest).map {
            if( it.status == 200L){
                it.data.filter { transaction ->
                    transaction.apmc == tradeDataRequest.apmcName
                }
            }else{
               emptyList()
            }
        }
    }





    private suspend fun groupTradeDataByCommodity(
        commodityTransactionList: List<CommodityTransaction>,
        apmcName: String,
        liveTradeList: Result<List<CommodityTransaction>, DataError>
    ): List<CommodityState> = withContext(Dispatchers.IO) {

        val liveTrade = when(liveTradeList){
            is Result.Success -> liveTradeList.data
            is Result.Error -> emptyList()
        }

        val liveTradeMap =  commodityTransactionList + liveTrade

        var minPriceInHistory = Double.NEGATIVE_INFINITY
        var maxPriceInHistory = Double.POSITIVE_INFINITY

        val groupedData = liveTradeMap
            .groupBy { it.commodity }
            .map { (commodity, trades) ->
                async {
                    CommodityState(
                        apmc = apmcName.lowercase().replaceFirstChar {
                            it.uppercase()
                        },
                        commodity = commodity.lowercase().replaceFirstChar {
                            it.uppercase()
                        },
                        image = "$IMG_BASE_URL_CROPS/$commodity.jpg",

                        tradeData = trades.map{
                            val max = it.maxPrice
                            val min = it.minPrice
                            if (max > maxPriceInHistory) {
                                maxPriceInHistory = max
                            }
                            if (min < minPriceInHistory) {
                                minPriceInHistory = min
                            }

                            it.toTradeReport()
                        }.sortedByDescending {
                            parseDate(it.createdAt)
                        }.distinctBy {
                            it.createdAt
                        }
                    )
                }
            }


        val newData = groupedData.awaitAll().map { commodityState ->
            async {

                val prices = commodityState.tradeData.map {
                    it.maxPrice
                }

                val priceThreads = calculatePriceChange(prices)


                if (priceThreads.isNotEmpty()) {

                    val paddedPriceThreads =
                        priceThreads + List(commodityState.tradeData.size - priceThreads.size) { 0.0f }

                    val firstThread = paddedPriceThreads.first()

                    val tradeReport =
                        commodityState.tradeData.zip(paddedPriceThreads) { trade, priceThread ->
                            trade.copy(
                                priceThread = priceThread,
                                priceColor = if (priceThread > 0) 0xFF1E8805.toInt() else 0xFFB00020.toInt()
                            )
                        }

                    commodityState.copy(
                        priceColor = if (firstThread > 0) 0xFF1E8805.toInt() else 0xFFB00020.toInt(),
                        currentPriceThread = if (firstThread > 0) "+${firstThread}%" else "${firstThread}%",
                        priceThreads = paddedPriceThreads,
                        tradeData = tradeReport,
                        maxPriceInHistory = maxPriceInHistory,
                        minPriceInHistory = minPriceInHistory
                    )
                } else {
                    commodityState.copy(
                        priceColor = 0xFF1E8805.toInt(),
                        currentPriceThread = "+00.00%",
                        priceThreads = emptyList()
                    )
                }

            }
        }

        return@withContext newData.awaitAll()
    }

    private suspend inline fun calculatePriceChange(prices: List<Double>): List<Float> = withContext(Dispatchers.IO) {
        val previousPrices = prices.drop(1)
        val currentPrices = prices.dropLast(1)

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