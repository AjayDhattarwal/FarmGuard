package com.ar.farmguard.services.insurance.auth.domain.models.remote

import com.ar.farmguard.services.insurance.auth.data.dto.OtpResponseSerializer
import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(
    val data: OtpData,
    val captcha: String
)


@Serializable
data class OtpData(
    val mobile: Long,
    val otp: Boolean,
)


@Serializable
data class OtpVerifyRequest(
    val data: OtpVerifyData,
    val captcha: String
)

@Serializable
data class OtpVerifyData(
    val mobile: Long,
    val otpType: String
)



@Serializable(with = OtpResponseSerializer::class)
data class OtpResponse(
    val status: Boolean = false,
    val error: String = "",
    val level: String? = null,
    val data: ResponseDataOTP? = null,
)

@Serializable
data class ResponseDataOTP(
    val otp: Int? = null,
    val verified: Boolean? =  null,
    val mobile: String? = null
)
