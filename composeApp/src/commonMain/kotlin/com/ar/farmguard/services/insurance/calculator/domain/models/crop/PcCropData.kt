package com.ar.farmguard.services.insurance.calculator.domain.models.crop

import kotlinx.serialization.Serializable

@Serializable
data class PcCropData(
    val cropID: String,
    val cropCode: String,
    val cropName: String,
    val pickingType: String,
    val categoryName: String,
    val cropType: String,
    val type: String?,
    val unit: String,
    val cropTypeCode: String
)