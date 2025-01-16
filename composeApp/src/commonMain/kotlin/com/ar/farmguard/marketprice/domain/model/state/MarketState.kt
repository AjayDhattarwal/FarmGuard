package com.ar.farmguard.marketprice.domain.model.state


data class MarketState(
    val state: String = "",
    val apmc: String = "",
    val commodityWithHistory: List<CommodityState> = emptyList(),
    val fromDate: String = "",
    val toDate : String = "",
    val commodityName: String = "-- Select Commodity --",
    val language: String = "en",
    val isUserDataAvailable: Boolean = false
)


