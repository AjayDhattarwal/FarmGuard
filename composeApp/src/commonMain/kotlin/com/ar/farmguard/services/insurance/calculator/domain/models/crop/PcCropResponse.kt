package com.ar.farmguard.services.insurance.calculator.domain.models.crop

import kotlinx.serialization.Serializable

@Serializable
data class PcCropResponse(
    val status: Boolean,
    val data: List<PcCropData>,
    val error: String?
)


