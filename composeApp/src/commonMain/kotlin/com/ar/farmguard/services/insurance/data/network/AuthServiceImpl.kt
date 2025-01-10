package com.ar.farmguard.services.insurance.data.network

import com.ar.farmguard.app.utils.BASE_URL
import com.ar.farmguard.app.utils.CAPTCHA_URL
import com.ar.farmguard.app.utils.DOMAIN
import com.ar.farmguard.app.utils.IS_LOGIN_URL
import com.ar.farmguard.app.utils.LOGIN_OTP_URL
import com.ar.farmguard.app.utils.LOGIN_WITH_OTP_URL
import com.ar.farmguard.services.insurance.domain.models.remote.LoginData
import com.ar.farmguard.services.insurance.domain.models.remote.LoginRequest
import com.ar.farmguard.services.insurance.domain.models.remote.OtpData
import com.ar.farmguard.services.insurance.domain.models.remote.OtpRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType



class AuthServiceImpl(
    private val client: HttpClient
){


    suspend fun fetchLoginState(): HttpResponse{
        return client.post(IS_LOGIN_URL){
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.AcceptEncoding, "gzip, deflate, br")
                append(HttpHeaders.AcceptLanguage, "en-US,en;q=0.9")
                append(HttpHeaders.Connection, "keep-alive")
                append("deviceType", "Web")
                append(HttpHeaders.Origin, BASE_URL)
                append(HttpHeaders.Referrer, BASE_URL)
            }
            setBody("")
        }
    }

    suspend fun getCaptcha(): HttpResponse {

        val response = client.get(CAPTCHA_URL) {
            headers {
                append(HttpHeaders.Accept, "image/svg+xml")
                append(HttpHeaders.Host, DOMAIN)
            }
        }
        return response

    }


    suspend fun getOtp(phoneNumber: Long, captcha: String): HttpResponse {
        val otpData = OtpData(phoneNumber, true)
        val otpRequest = OtpRequest(otpData, captcha)
        return client.post(LOGIN_OTP_URL) {
            contentType(ContentType.Application.Json)
            setBody(otpRequest)
        }
    }

    suspend fun loginUser(phoneNumber: Long, otp: Long): HttpResponse {
        val loginData = LoginData(phoneNumber, otp)
        val otpRequest = LoginRequest(loginData)
        return client.post(LOGIN_WITH_OTP_URL) {
            contentType(ContentType.Application.Json)
            setBody(otpRequest)
        }
    }
}