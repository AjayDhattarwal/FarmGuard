package com.ar.farmguard.services.insurance.calculator.domain.models.calculate

import kotlinx.serialization.Serializable

@Serializable
data class PcResponse(
    val status: Boolean,
    val data: List<PcData>,
    val error: String?
)
