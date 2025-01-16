package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class EnamApmc(
    val data: List<EnamApmcData> = emptyList(),
    val status: Long = 0L,
)

