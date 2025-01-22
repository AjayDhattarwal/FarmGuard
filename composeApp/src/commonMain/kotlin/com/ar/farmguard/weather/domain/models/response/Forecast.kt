package com.ar.farmguard.weather.domain.models.response

import com.ar.farmguard.weather.domain.models.response.forecast.ForecastData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecast (
    @SerialName("forecastday" ) val forecastData : List<ForecastData> = emptyList()

)