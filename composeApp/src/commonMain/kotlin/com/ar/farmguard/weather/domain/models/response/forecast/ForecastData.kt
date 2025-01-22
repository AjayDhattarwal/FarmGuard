package com.ar.farmguard.weather.domain.models.response.forecast

import com.ar.farmguard.core.presentation.getDayOfWeekFromTimestamp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastData(
    @SerialName("date"       ) val date      : String?         = null,
    @SerialName("date_epoch" ) val dateEpoch : Long?            = null,
    @SerialName("day"        ) val day       : ForecastDay?            = ForecastDay(),
    @SerialName("astro"      ) val astro     : ForecastAstro?  = ForecastAstro(),
    @SerialName("hour"       ) val hour      : List<ForecastHour> = emptyList()
){
    val dayOfWeek: String
        get() = dateEpoch?.let { getDayOfWeekFromTimestamp(it) } ?: date ?: ""
}
