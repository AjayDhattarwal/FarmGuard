package com.ar.farmguard.services.insurance.domain.models.remote

import com.ar.farmguard.services.insurance.data.dto.OtpResponseSerializer
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


@Serializable(with = OtpResponseSerializer::class)
data class OtpResponse(
    val status: Boolean = false,
    val error: String = "",
    val level: String? = null,
    val data: String? = null,
)

@Serializable
data class ResponseDataOTP(
    val otp: Int? = null,
)