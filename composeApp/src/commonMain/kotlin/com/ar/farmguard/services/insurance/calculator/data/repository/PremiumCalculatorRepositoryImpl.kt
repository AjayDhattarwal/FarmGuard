package com.ar.farmguard.services.insurance.calculator.data.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.calculator.data.network.PremiumCalculatorApi
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.PcResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.crop.PcCropResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.district.PcDistrictResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.sssy.SssyResponse
import com.ar.farmguard.services.insurance.calculator.domain.repository.PremiumCalculatorRepository

class PremiumCalculatorRepositoryImpl(
    private val api: PremiumCalculatorApi
): PremiumCalculatorRepository {


    override suspend fun getSssyList(): Result<SssyResponse, DataError.Remote> {
        return api.getSssyList()
    }

    override suspend fun getDistrict(
        stateId: String,
        sssyId: String
    ): Result<PcDistrictResponse, DataError.Remote> {
        return api.getDistrict(stateId, sssyId)
    }

    override suspend fun getCrop(
        sssyID: String,
        districtID: String
    ): Result<PcCropResponse, DataError.Remote> {
        return api.getCrop(sssyID, districtID)
    }

    override suspend fun calculatePremium(
        sssyID: String,
        districtID: String,
        cropID: String
    ): Result<PcResponse, DataError.Remote> {
        return api.calculatePremium(sssyID, districtID, cropID )
    }


}