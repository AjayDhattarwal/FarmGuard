package com.ar.farmguard.marketprice.domain.model.dto

import com.ar.farmguard.marketprice.domain.model.MarketState
import com.ar.farmguard.marketprice.domain.model.remote.TradeDataRequest

fun MarketState.toTradeDataRequest(): TradeDataRequest{
    return TradeDataRequest(
        language = language,
        apmcName = apmc,
        stateName = state,
        fromDate = fromDate,
        toDate = toDate,
        commodityName = commodityName
    )
}