package com.ar.farmguard.marketprice.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.marketprice.domain.model.CommodityState
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest

interface EnamMandiRepository {

    suspend fun getTradeList(tradeDataRequest: TradeDataRequest): Result<List<CommodityState>?, DataError.Remote>

}