package com.ar.farmguard.marketprice.domain.model

import com.ar.farmguard.marketprice.domain.model.remote.TradeData

data class CommodityState(
    val commodity: String,
    val image: String = "",
    val tradeData: List<TradeData>,
    val priceColor: Int = 0xFF1E8805.toInt(),
    val currentPriceThread: String = "",
    val priceThreads: List<Float> = emptyList(),
    val apmc: String = ""
)
