package com.ar.farmguard.services.scheme.data.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeResponse
import com.ar.farmguard.services.scheme.domain.network.SchemeApi
import com.ar.farmguard.services.scheme.domain.repository.SchemeRepository

class SchemeRepositoryImpl(
    private val api: SchemeApi
): SchemeRepository {

    override suspend fun fetchScheme(
        from: Int,
        state: String,
        size: Int,
        lang: String
    ): Result<SchemeResponse, DataError> {
        return api.fetchScheme(
            from = from,
            state = state,
            size = size,
            lang = lang
        )
    }
}