package com.ar.farmguard.marketprice.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.marketprice.domain.model.remote.CommodityTransaction
import com.ar.farmguard.marketprice.domain.model.remote.EnamApmc
import com.ar.farmguard.marketprice.domain.model.remote.EnamState
import com.ar.farmguard.marketprice.domain.model.state.CommodityState
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest

interface EnamMandiRepository {

    suspend fun getTradeList(tradeDataRequest: TradeDataRequest): Result<List<CommodityState>?, DataError.Remote>

    suspend fun getStateData(): Result<EnamState, DataError.Remote>

    suspend fun getApmcData(stateId: String): Result<EnamApmc, DataError.Remote>

    suspend fun getLiveTradeList(tradeDataRequest: TradeDataRequest): Result<List<CommodityTransaction>?, DataError>

}