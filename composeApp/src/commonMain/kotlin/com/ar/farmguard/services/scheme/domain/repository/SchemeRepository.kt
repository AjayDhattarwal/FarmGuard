package com.ar.farmguard.services.scheme.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeResponse

interface SchemeRepository {

    suspend fun fetchScheme(
        from: Int = 0,
        state: String,
        size: Int = 20,
        lang: String = "en",
    ): Result<SchemeResponse, DataError>


}