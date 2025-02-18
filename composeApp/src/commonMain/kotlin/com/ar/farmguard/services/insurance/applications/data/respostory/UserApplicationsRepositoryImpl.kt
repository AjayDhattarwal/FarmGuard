package com.ar.farmguard.services.insurance.applications.data.respostory

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyData
import com.ar.farmguard.services.insurance.applications.domain.network.UserApplicationsApi
import com.ar.farmguard.services.insurance.applications.domain.repostory.UserApplicationsRepository

class UserApplicationsRepositoryImpl(
    private val api : UserApplicationsApi
): UserApplicationsRepository {


    override suspend fun getApplications(
        sssyID: String,
        farmerID: String
    ): Result<List<UserPolicyData>, DataError.Remote> {

        var status = false
        val result = api.getApplications(sssyID, farmerID).onSuccess {
            status = it.status == true
        }

        return if(status){
            result.map { it.data }
        } else {
            Result.Error(DataError.Remote.UNAUTHORIZED)
        }

    }


}