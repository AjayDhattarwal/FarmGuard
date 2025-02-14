package com.ar.farmguard.services.scheme.data.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.services.scheme.domain.model.SchemeDetails
import com.ar.farmguard.services.scheme.domain.network.SchemeDetailsApi
import com.ar.farmguard.services.scheme.domain.repository.SchemeDetailsRepository

class SchemeDetailsRepositoryImpl(
    private val api: SchemeDetailsApi
): SchemeDetailsRepository {

    override suspend fun getSchemeDetails(
        id: String,
        buildID: String
    ): Result<SchemeDetails, DataError.Remote> {
        return api.getSchemeDetails(id, buildID)
    }

    override suspend fun fetchBuildID(): Result<String?, DataError.Remote> {
        return  api.fetchBuildID().map {
            try {
                val regex = ""","buildId":"([^"]*)","""
                Regex(regex).find(it)?.groupValues?.get(1)
            }catch (e: Exception){
                null
            }
        }
    }

}