package com.ar.farmguard.services.insurance.applications.domain.repostory

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyData

interface UserApplicationsRepository {
    suspend fun getApplications(sssyID: String, farmerID: String): Result<List<UserPolicyData>, DataError.Remote>
}