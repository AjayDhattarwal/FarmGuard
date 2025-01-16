package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class TradeDataResponse(
    val data: List<CommodityTransaction> = emptyList(),
    val status: Long? = 200,
)