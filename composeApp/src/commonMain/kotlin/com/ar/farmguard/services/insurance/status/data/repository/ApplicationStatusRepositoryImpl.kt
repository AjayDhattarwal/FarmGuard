package com.ar.farmguard.services.insurance.status.data.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.services.insurance.auth.domain.models.remote.CaptchaResponse
import com.ar.farmguard.services.insurance.status.data.network.ApplicationStatusApiImpl
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusResponse
import com.ar.farmguard.services.insurance.status.domain.network.ApplicationStatusApi
import com.ar.farmguard.services.insurance.status.domain.repository.ApplicationStatusRepository
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.json.Json

class ApplicationStatusRepositoryImpl(
    private val api: ApplicationStatusApi
): ApplicationStatusRepository {


    override suspend fun getCaptcha(): Result<ByteArray, DataError.Remote> {


        return  api.getCaptcha().map {

            val captchaResponse: CaptchaResponse  = Json.decodeFromString(it.decodeToString())

            if(captchaResponse.data.isNotEmpty()){
                captchaResponse.data.toByteArray()
            }else{
                byteArrayOf()
            }
        }
    }

    override suspend fun checkPolicyStatus(
        policyID: String,
        captcha: String
    ): Result<ApplicationStatusResponse, DataError.Remote> {
        return api.checkPolicyStatus(policyID, captcha)
    }
}