package com.ar.farmguard.marketprice.domain.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnamStateData(
    @SerialName("state_name")
    val name: String,
    @SerialName("state_id")
    val stateId: String,
)