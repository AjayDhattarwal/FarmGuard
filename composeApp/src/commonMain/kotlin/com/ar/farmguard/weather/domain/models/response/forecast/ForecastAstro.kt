package com.ar.farmguard.weather.domain.models.response.forecast

import com.ar.farmguard.app.utils.IMG_BASE_URL_WEATHER
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ForecastAstro(
    @SerialName("sunrise"           ) val sunrise          : String? = null,
    @SerialName("sunset"            ) val sunset           : String? = null,
    @SerialName("moonrise"          ) val moonrise         : String? = null,
    @SerialName("moonset"           ) val moonset          : String? = null,
    @SerialName("moon_phase"        ) val moonPhase        : String? = null,
    @SerialName("moon_illumination" ) val moonIllumination : Float?    = null,
    @SerialName("is_moon_up"        ) val isMoonUp         : Int?    = null,
    @SerialName("is_sun_up"         ) val isSunUp          : Int?    = null
)