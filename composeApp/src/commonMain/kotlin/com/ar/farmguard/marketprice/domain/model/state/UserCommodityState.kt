package com.ar.farmguard.marketprice.domain.model.state

data class UserCommodityState(
    val state: String = "",
    val apmc: String = "",
    val language: String = "",
    val stateSelectionList: List<String> = emptyList(),
    val apmcSelectionList: List<String> = emptyList()
)
