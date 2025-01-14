package com.ar.farmguard.services.insurance.status.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusResponse

interface ApplicationStatusRepository {

    suspend fun getCaptcha() : Result<ByteArray, DataError.Remote>
    suspend fun checkPolicyStatus(policyID: String, captcha: String): Result<ApplicationStatusResponse, DataError.Remote>
}