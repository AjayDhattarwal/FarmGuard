package com.ar.farmguard.services.insurance.calculator.data.network

import com.ar.farmguard.app.utils.PC_CALCULATE
import com.ar.farmguard.app.utils.PC_CROP
import com.ar.farmguard.app.utils.PC_DISTRICTS
import com.ar.farmguard.app.utils.PC_SSSY
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.PcResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.crop.PcCropResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.district.PcDistrictResponse
import com.ar.farmguard.services.insurance.calculator.domain.models.sssy.SssyResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.append


class PremiumCalculatorApi(
    private val client: HttpClient
)  {

    suspend fun getSssyList(): Result<SssyResponse, DataError.Remote> {

        return safeCall {
            client.get(PC_SSSY)
        }
    }


    suspend fun getDistrict(stateId: String, sssyId: String): Result<PcDistrictResponse, DataError.Remote> {
        return safeCall {
            client.get(PC_DISTRICTS){
                url {
                    parameters.append("stateID", stateId )
                    parameters.append("sssyID", sssyId )
                }
            }
        }
    }

    suspend fun getCrop(sssyID: String, districtID: String): Result<PcCropResponse, DataError.Remote> {
        return safeCall {
            client.get(PC_CROP){
                url {
                    parameters.append("sssyID", sssyID)
                    parameters.append("districtID", districtID)
                }
            }
        }
    }

    suspend fun calculatePremium(
        sssyID: String,
        districtID: String,
        cropID: String
    ): Result<PcResponse, DataError.Remote> {
        return safeCall {
            client.get(PC_CALCULATE) {
                url {
                    parameters.append("sssyID", sssyID)
                    parameters.append("districtID", districtID)
                    parameters.append("cropID", cropID)
                }
            }
        }
    }

}