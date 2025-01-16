package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommodityTransaction(
    val id: String,
    val state: String,
    val apmc: String,
    val commodity: String,
    @SerialName("min_price")
    val minPrice: Double,
    @SerialName("modal_price")
    val modalPrice: Double,
    @SerialName("max_price")
    val maxPrice: Double,
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
