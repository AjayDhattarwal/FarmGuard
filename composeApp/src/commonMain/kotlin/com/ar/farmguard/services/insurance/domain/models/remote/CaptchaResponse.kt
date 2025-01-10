package com.ar.farmguard.services.insurance.domain.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class CaptchaResponse(
    val status: Boolean,
    val error: String,
    val data: String
)
