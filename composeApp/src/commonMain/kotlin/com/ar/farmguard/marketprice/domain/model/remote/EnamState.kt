package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class EnamState(
    val status: Long = 0,
    val data: List<EnamStateData> = emptyList()
)

