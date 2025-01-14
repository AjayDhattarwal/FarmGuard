package com.ar.farmguard.marketprice.domain.network

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataResponse

interface EnamMandiApi {

    suspend fun getTradeList(tradeDataRequest: TradeDataRequest): Result<TradeDataResponse, DataError.Remote>

}