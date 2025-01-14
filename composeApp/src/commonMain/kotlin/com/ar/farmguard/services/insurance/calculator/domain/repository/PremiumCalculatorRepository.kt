package com.ar.farmguard.services.insurance.calculator.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.PcResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.crop.PcCropResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.district.PcDistrictResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.sssy.SssyResponse

interface PremiumCalculatorRepository {
    suspend fun getSssyList(): Result<SssyResponse, DataError.Remote>

    suspend fun getDistrict(
        stateId: String,
        sssyId: String
    ): Result<PcDistrictResponse, DataError.Remote>

    suspend fun getCrop(
        sssyID: String,
        districtID: String
    ): Result<PcCropResponse, DataError.Remote>

    suspend fun calculatePremium(
        sssyID: String,
        districtID: String,
        cropID: String
    ): Result<PcResponse, DataError.Remote>

}