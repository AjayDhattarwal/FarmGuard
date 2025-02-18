package com.ar.farmguard.services.insurance.applications.domain.network

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyResponse

interface UserApplicationsApi {
    suspend fun getApplications(sssyID: String, farmerID: String): Result<UserPolicyResponse, DataError.Remote>
}