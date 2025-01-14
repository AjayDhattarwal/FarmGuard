package com.ar.farmguard.marketprice.domain.model


data class MarketState(
    val state: String = "HARYANA",
    val apmc: String = "SIRSA",
    val commodityWithHistory: List<CommodityState> = emptyList(),    // perform search on this
    val fromDate: String = "2025-01-07",
    val toDate : String = "2025-01-13",
    val commodityName: String = "-- Select Commodity --",
    val language: String = "en"
)


