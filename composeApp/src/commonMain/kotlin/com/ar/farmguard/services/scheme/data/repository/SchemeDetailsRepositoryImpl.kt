package com.ar.farmguard.services.scheme.data.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails
import com.ar.farmguard.services.scheme.domain.network.SchemeDetailsApi
import com.ar.farmguard.services.scheme.domain.repository.SchemeDetailsRepository

class SchemeDetailsRepositoryImpl(
    private val api: SchemeDetailsApi
): SchemeDetailsRepository {

    override suspend fun getSchemeDetails(id: String): Result<SchemeDetails, DataError.Remote> {
        return api.getSchemeDetails(id)
    }

}