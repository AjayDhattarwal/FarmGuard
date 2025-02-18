package com.ar.farmguard.services.insurance.applications.data.network

import com.ar.farmguard.app.utils.POLICY_LIST
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyResponse
import com.ar.farmguard.services.insurance.applications.domain.network.UserApplicationsApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class UserApplicationsApiImpl(
    private val client: HttpClient
): UserApplicationsApi {

    override suspend fun getApplications(
        sssyID: String,
        farmerID: String
    ): Result<UserPolicyResponse, DataError.Remote> {
        return safeCall(isDecrypt = true) {
            client.get(POLICY_LIST){
                url {
                    parameters.append("listType", "POLICY_LIST")
                    parameters.append("sssyID", sssyID)
                    parameters.append("farmerID", farmerID)
                }
            }
        }
    }

}