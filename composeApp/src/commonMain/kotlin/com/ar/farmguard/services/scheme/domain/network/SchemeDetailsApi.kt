package com.ar.farmguard.services.scheme.domain.network

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails

interface SchemeDetailsApi {

    suspend fun getSchemeDetails(id: String): Result<SchemeDetails, DataError.Remote>

}