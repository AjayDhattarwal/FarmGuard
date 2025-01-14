package com.ar.farmguard.services.insurance.status.data.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.onError
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.services.insurance.auth.domain.models.remote.CaptchaResponse
import com.ar.farmguard.services.insurance.status.data.network.ApplicationStatusApiImpl
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusResponse
import com.ar.farmguard.services.insurance.status.domain.repository.ApplicationStatusRepository
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.json.Json

class ApplicationStatusRepositoryImpl(
    private val api: ApplicationStatusApiImpl
): ApplicationStatusRepository {


    override suspend fun getCaptcha(): Result<ByteArray, DataError.Remote> {
        var captchaResponse: CaptchaResponse? = null

        api.getCaptcha().onSuccess {
            captchaResponse = Json.decodeFromString(it.decodeToString())
        } .onError {
            captchaResponse = CaptchaResponse(status = false, error = "Something Went Wrong", data = "")
        }

        return if(captchaResponse?.data?.isNotEmpty() == true){
            Result.Success(captchaResponse!!.data.toByteArray())
        }else{
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun checkPolicyStatus(
        policyID: String,
        captcha: String
    ): Result<ApplicationStatusResponse, DataError.Remote> {
        return api.checkPolicyStatus(policyID, captcha)
    }
}