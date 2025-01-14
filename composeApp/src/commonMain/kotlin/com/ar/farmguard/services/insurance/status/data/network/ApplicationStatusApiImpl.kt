package com.ar.farmguard.services.insurance.status.data.network

import com.ar.farmguard.app.utils.APPLICATION_STATUS
import com.ar.farmguard.app.utils.CAPTCHA_URL
import com.ar.farmguard.app.utils.DOMAIN
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusResponse
import com.ar.farmguard.services.insurance.status.domain.network.ApplicationStatusApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.readRawBytes
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class ApplicationStatusApiImpl(
    private val httpClient: HttpClient
): ApplicationStatusApi {
    override suspend fun getCaptcha(): Result<ByteArray, DataError.Remote> {
        return try {
            val response = httpClient.get(CAPTCHA_URL){
                headers {
                    append(HttpHeaders.Accept, "image/svg+xml")
                    append(HttpHeaders.Host, DOMAIN)
                }
            }
            Result.Success(response.readRawBytes())
        } catch (e: Exception){
            Result.Error(DataError.Remote.UNKNOWN)
        }

    }

    override suspend fun checkPolicyStatus(
        policyID: String,
        captcha: String
    ): Result<ApplicationStatusResponse, DataError.Remote> {
       return safeCall {
           httpClient.post(APPLICATION_STATUS){
               contentType(ContentType.Application.Json)
               setBody(mapOf("policyID" to policyID, "captcha" to captcha))
           }
       }
    }


}