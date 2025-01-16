package com.ar.farmguard.marketprice.data.network

import com.ar.farmguard.app.utils.ENAM_APMC_LIST
import com.ar.farmguard.app.utils.ENAM_LIVE_TRADE
import com.ar.farmguard.app.utils.ENAM_STATE_LIST
import com.ar.farmguard.app.utils.ENAM_TRADE_LIST
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.marketprice.domain.model.remote.EnamApmc
import com.ar.farmguard.marketprice.domain.model.remote.EnamState
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataResponse
import com.ar.farmguard.marketprice.domain.network.EnamMandiApi
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.withCharset
import io.ktor.utils.io.charsets.Charsets

class EnamMandiApiImpl(
    private val client: HttpClient
): EnamMandiApi {

    override suspend fun getTradeList(tradeDataRequest: TradeDataRequest): Result<TradeDataResponse, DataError.Remote> {
        val formData = Parameters.build {
            append("language", tradeDataRequest.language)
            append("stateName", tradeDataRequest.stateName)
            append("apmcName", tradeDataRequest.apmcName)
            append("commodityName", tradeDataRequest.commodityName)
            append("fromDate", tradeDataRequest.fromDate)
            append("toDate", tradeDataRequest.toDate)
        }


        return safeCall(isDecrypt = false, tryWithSting = true ) {
            client.post(ENAM_TRADE_LIST) {
                contentType(ContentType.Application.FormUrlEncoded.withCharset(Charsets.UTF_8))
                setBody(formData.formUrlEncode())
            }
        }
    }

    override suspend fun getLiveTradeList(tradeDataRequest: TradeDataRequest):Result<TradeDataResponse, DataError>{
        val formData = Parameters.build {
            append("language", tradeDataRequest.language)
            append("stateName", tradeDataRequest.stateName)
            append("fromDate", tradeDataRequest.fromDate)
            append("toDate", tradeDataRequest.toDate)
        }

        return safeCall(isDecrypt = false, tryWithSting = true ) {
            client.post(ENAM_LIVE_TRADE) {
                contentType(ContentType.Application.FormUrlEncoded.withCharset(Charsets.UTF_8))
                setBody(formData.formUrlEncode())
            }
        }
    }

    override suspend fun getStateData(): Result<EnamState, DataError.Remote> {
        return safeCall(isDecrypt = false, tryWithSting = true ) {
            client.post(ENAM_STATE_LIST)
        }
    }

    override suspend fun getApmcData(stateId: String): Result<EnamApmc, DataError.Remote> {
        val formData = Parameters.build {
            append("state_id", stateId)
        }
        return safeCall(isDecrypt = false, tryWithSting = true) {
            client.post(ENAM_APMC_LIST){
                contentType(ContentType.Application.FormUrlEncoded.withCharset(Charsets.UTF_8))
                setBody(formData.formUrlEncode())
            }
        }
    }

}