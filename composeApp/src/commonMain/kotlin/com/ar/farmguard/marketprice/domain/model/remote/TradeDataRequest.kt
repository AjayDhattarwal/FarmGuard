package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class TradeDataRequest(
    val language: String,
    val stateName: String,
    val apmcName: String,
    val commodityName: String,
    val fromDate: String,
    val toDate: String
)