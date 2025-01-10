package com.ar.farmguard.services.insurance.domain.repository

import com.ar.farmguard.services.insurance.domain.models.remote.CaptchaResponse


interface AuthRepository {

    suspend fun getCaptcha(): CaptchaResponse

    suspend fun getOtp(phoneNumber: Long, captcha: String): Pair<Boolean, String>

    suspend fun loginUser(phoneNumber: Long, otp: Long): Pair<Boolean, String>

}
