package com.ar.farmguard.services.scheme.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails

interface SchemeDetailsRepository {

    suspend fun getSchemeDetails(id: String): Result<SchemeDetails, DataError.Remote>

}