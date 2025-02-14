package com.ar.farmguard.services.scheme.data.network

import com.ar.farmguard.app.utils.MY_SCHEME_DETAILS_URL
import com.ar.farmguard.app.utils.MY_SCHEME_URL
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails
import com.ar.farmguard.services.scheme.domain.network.SchemeDetailsApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.path

class SchemeDetailsApiImpl(
    private val httpClient: HttpClient
): SchemeDetailsApi {

    override suspend fun getSchemeDetails(id: String, buildID: String): Result<SchemeDetails, DataError.Remote> {
        return safeCall {
            httpClient.get(MY_SCHEME_DETAILS_URL) {
                url {
                    path("_next","data",buildID,"en", "schemes", "$id.json")
                    parameters.append("slug", id)
                }
            }
        }
    }


    override suspend fun fetchBuildID(): Result<String, DataError.Remote> {
        return try {
            val response = httpClient.get(MY_SCHEME_URL)
            if(response.status.value == 200){
                Result.Success(response.bodyAsText())
            }else{
                Result.Error(DataError.Remote.UNKNOWN)
            }
        }catch (e: Exception){
            Result.Error(DataError.Remote.UNKNOWN)

        }
    }
}