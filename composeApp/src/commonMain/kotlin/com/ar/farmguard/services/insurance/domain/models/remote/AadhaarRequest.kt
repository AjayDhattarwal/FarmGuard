package com.ar.farmguard.services.insurance.domain.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class AadhaarRequest(
    val status: Boolean,
    val error: String
)
