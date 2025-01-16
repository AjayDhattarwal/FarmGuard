package com.ar.farmguard.marketprice.domain.model.dto

import com.ar.farmguard.core.presentation.getTodayDate
import com.ar.farmguard.marketprice.domain.model.state.MarketState
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest

fun MarketState.toTradeDataRequest(isLive: Boolean = false): TradeDataRequest{
    return TradeDataRequest(
        language = language,
        apmcName = apmc,
        stateName = state,
        fromDate = fromDate,
        toDate = if(isLive) getTodayDate() else toDate,
        commodityName = commodityName
    )
}