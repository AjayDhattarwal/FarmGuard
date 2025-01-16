package com.ar.farmguard.marketprice.domain.model.state

import com.ar.farmguard.marketprice.domain.model.remote.CommodityTransaction

data class CommodityState(
    val commodity: String,
    val image: String = "",
    val tradeData: List<TradeReport> = emptyList(),
    val priceColor: Int = 0xFF1E8805.toInt(),
    val currentPriceThread: String = "",
    val priceThreads: List<Float> = emptyList(),
    val apmc: String = "",
    val minPriceInHistory: Double = 0.0 ,
    val maxPriceInHistory: Double = 0.0,
)


data class TradeReport(
    val priceColor: Int,
    val priceThread: Float,
    val id: String,
    val state: String,
    val apmc: String,
    val commodity: String,
    val minPrice: Double,
    val modalPrice: Double,
    val maxPrice: Double,
    val commodityArrivals: String,
    val commodityTraded: String,
    val createdAt: String,
    val status: String,
    val commodityUom: String,
){
    val priceThreadString: String
        get() = if(priceThread > 0) "+${priceThread}%" else "${priceThread}%"

    val minAvgPriceString: String
            get() = "min: ₹${minPrice} -  avg: ₹${modalPrice}"

    val maxPriceString: String
        get() = "₹${maxPrice} / ${ if(commodityUom == "Quintal") "QTL" else commodityUom}"
}

fun CommodityTransaction.toTradeReport() = TradeReport(
    priceColor =  0,
    priceThread = 0.0f,
    id = id,
    state = state,
    apmc = apmc,
    commodity = commodity,
    minPrice = minPrice,
    modalPrice = modalPrice,
    maxPrice = maxPrice,
    commodityArrivals = commodityArrivals,
    commodityTraded = commodityTraded,
    createdAt = createdAt,
    status = status,
    commodityUom = commodityUom
)