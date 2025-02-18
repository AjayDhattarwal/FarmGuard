package com.ar.farmguard.services.insurance.auth.data.network

import com.ar.farmguard.app.utils.CAPTCHA_URL
import com.ar.farmguard.app.utils.INSURANCE_DOMAIN
import com.ar.farmguard.app.utils.IS_LOGIN_URL
import com.ar.farmguard.app.utils.LOGIN_OTP_URL
import com.ar.farmguard.app.utils.LOGIN_WITH_OTP_URL
import com.ar.farmguard.app.utils.VERIFY_MOBILE
import com.ar.farmguard.services.insurance.auth.domain.models.remote.LoginData
import com.ar.farmguard.services.insurance.auth.domain.models.remote.LoginRequest
import com.ar.farmguard.services.insurance.auth.domain.models.remote.OtpData
import com.ar.farmguard.services.insurance.auth.domain.models.remote.OtpRequest
import com.ar.farmguard.services.insurance.auth.domain.models.remote.OtpVerifyData
import com.ar.farmguard.services.insurance.auth.domain.models.remote.OtpVerifyRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType



class AuthService(
    private val client: HttpClient
){

    suspend fun fetchLoginState(): HttpResponse{
        return client.post(IS_LOGIN_URL){
            headers{
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }

    suspend fun getCaptcha(): HttpResponse {

        val response = client.get(CAPTCHA_URL) {
            headers {
                append(HttpHeaders.Accept, "image/svg+xml")
                append(HttpHeaders.Host, INSURANCE_DOMAIN)
            }
        }
        return response

    }


    suspend fun getOtp(phoneNumber: Long, captcha: String, verifyOnly: Boolean): HttpResponse {

        val url = if (verifyOnly) VERIFY_MOBILE else LOGIN_OTP_URL
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(
                if(verifyOnly) {
                    val otpData = OtpVerifyData(phoneNumber, otpType = "SMS")
                    OtpVerifyRequest(otpData, captcha)
                }else{
                    val otpData = OtpData(phoneNumber, true)
                    OtpRequest(otpData, captcha)
                }
            )
            headers{
                remove(HttpHeaders.Cookie)
            }
        }
    }

    suspend fun loginUser(phoneNumber: Long, otp: Long, verifyOnly: Boolean): HttpResponse {
        val loginData = LoginData(phoneNumber, otp)
        val otpRequest = LoginRequest(loginData)
        return client.post(if (verifyOnly) VERIFY_MOBILE else LOGIN_WITH_OTP_URL) {
            contentType(ContentType.Application.Json)
            setBody(otpRequest)
        }
    }
}