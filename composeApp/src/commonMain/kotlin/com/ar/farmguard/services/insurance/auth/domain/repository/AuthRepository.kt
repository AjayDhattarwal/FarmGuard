package com.ar.farmguard.services.insurance.auth.domain.repository

import com.ar.farmguard.services.insurance.auth.domain.models.remote.CaptchaResponse
import com.ar.farmguard.services.insurance.auth.domain.models.remote.LoginCheckResponse
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message


interface AuthRepository {

    suspend fun getCaptcha(): CaptchaResponse

    suspend fun checkLoginState(): Pair<Boolean, LoginCheckResponse?>

    suspend fun getOtp(phoneNumber: Long, captcha: String, verifyOnly: Boolean = false): Pair<Boolean, Message>

    suspend fun verifyOtp(phoneNumber: Long, otp: Long, verifyOnly: Boolean = false): Pair<Boolean, Message>

}
