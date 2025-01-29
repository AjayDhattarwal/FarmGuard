package com.ar.farmguard.services.scheme.data.network

import com.ar.farmguard.app.utils.MY_SCHEME_DETAILS_URL
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails
import com.ar.farmguard.services.scheme.domain.network.SchemeDetailsApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class SchemeDetailsApiImpl(
    private val httpClient: HttpClient
): SchemeDetailsApi {

    override suspend fun getSchemeDetails(id: String): Result<SchemeDetails, DataError.Remote> {
        return safeCall {
            httpClient.get("${MY_SCHEME_DETAILS_URL}$id.json?slug=$id")
        }
    }
}