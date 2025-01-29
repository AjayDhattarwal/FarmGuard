package com.ar.farmguard.services.scheme.data.network

import com.ar.farmguard.app.utils.MY_SCHEME_BASE_URL
import com.ar.farmguard.app.utils.MY_SCHEME_KEY
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeResponse
import com.ar.farmguard.services.scheme.domain.network.SchemeApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.http.encodeURLParameter
import io.ktor.http.encodeURLQueryComponent
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.encodeUtf8

class SchemeApiImpl(
    private val httpClient: HttpClient
): SchemeApi {

    override suspend fun fetchScheme(
        from: Int,
        state: String,
        size: Int,
        lang: String
    ): Result<SchemeResponse, DataError> {

        val encodedQuery = """
            [
                {"identifier":"beneficiaryState","value":"All"},
                {"identifier":"beneficiaryState","value":"$state"},
                {"identifier":"schemeCategory","value":"Agriculture,Rural & Environment"}
            ]
        """.trimIndent()



        return safeCall {
            httpClient.get(MY_SCHEME_BASE_URL) {
                url {
                    parameter("from", from)
                    parameter("q", encodedQuery)
                    parameter("size", size)
                    parameters.append("lang", lang)
                    parameters.append("sort", "multiple_sort")
                }
                headers {
                    append("x-api-key", MY_SCHEME_KEY)
                }
            }
        }
    }

}