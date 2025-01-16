package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnamApmcData(
    @SerialName("apmc_id")
    val apmcId: String ? = null,
    @SerialName("apmc_name")
    val apmcName: String ? = null,
)
