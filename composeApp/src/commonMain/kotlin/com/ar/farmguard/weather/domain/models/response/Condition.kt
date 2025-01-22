package com.ar.farmguard.weather.domain.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    @SerialName("text" ) val text : String = "",
    @SerialName("icon" ) val icon : String = "",
    @SerialName("code" ) val code : Int    = 0
)
