package com.ar.farmguard.services.insurance.domain.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(
    val data: OtpData,
    val captcha: String
)

@Serializable
data class OtpData(
    val mobile: Long,
    val otp: Boolean
)


@Serializable
data class OtpResponse(
    val status: Boolean = true,
    val error: String = "",
    val level: String? = null
//    val data: ResponseOTP,
)

@Serializable
data class ResponseOTP(
    val otp: String,
)