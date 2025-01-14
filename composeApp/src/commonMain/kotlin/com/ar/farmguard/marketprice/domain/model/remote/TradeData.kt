package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TradeData(
    val id: String,
    val state: String,
    val apmc: String,
    val commodity: String,
    @SerialName("min_price")
    val minPrice: String,
    @SerialName("modal_price")
    val modalPrice: String,
    @SerialName("max_price")
    val maxPrice: String,
    @SerialName("commodity_arrivals")
    val commodityArrivals: String,
    @SerialName("commodity_traded")
    val commodityTraded: String,
    @SerialName("created_at")
    val createdAt: String,
    val status: String,
    @SerialName("Commodity_Uom")
    val commodityUom: String,
)
