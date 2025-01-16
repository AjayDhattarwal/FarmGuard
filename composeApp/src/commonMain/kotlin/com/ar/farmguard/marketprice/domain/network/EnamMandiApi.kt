package com.ar.farmguard.marketprice.domain.network

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.marketprice.domain.model.remote.EnamApmc
import com.ar.farmguard.marketprice.domain.model.remote.EnamState
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataResponse

interface EnamMandiApi {

    suspend fun getTradeList(tradeDataRequest: TradeDataRequest): Result<TradeDataResponse, DataError.Remote>

    suspend fun getStateData(): Result<EnamState, DataError.Remote>

    suspend fun getApmcData(stateId: String): Result<EnamApmc, DataError.Remote>


    suspend fun getLiveTradeList(tradeDataRequest: TradeDataRequest): Result<TradeDataResponse, DataError>
}