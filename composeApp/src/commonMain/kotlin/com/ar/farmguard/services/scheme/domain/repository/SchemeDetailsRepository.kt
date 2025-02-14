package com.ar.farmguard.services.scheme.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails

interface SchemeDetailsRepository {

    suspend fun getSchemeDetails(id: String, buildID: String): Result<SchemeDetails, DataError.Remote>

    suspend fun fetchBuildID(): Result<String?, DataError.Remote>

}