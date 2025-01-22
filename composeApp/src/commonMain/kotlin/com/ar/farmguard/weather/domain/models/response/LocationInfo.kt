package com.ar.farmguard.weather.domain.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationInfo (
    val name           : String? = null,
    val region         : String? = null,
    val country        : String? = null,
    val lat            : Double? = null,
    val lon            : Double? = null,
    val tzId           : String? = null,
    val localtimeEpoch : Long?   = null,
    val localtime      : String? = null

)