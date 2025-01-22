package com.ar.farmguard.home.domain.model

import com.ar.farmguard.weather.domain.models.response.CurrentWeather
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import kotlinx.serialization.Serializable

@Serializable
data class HomeWeatherResponse(
    val location : LocationInfo? = LocationInfo(),
    val current  : CurrentWeather?  = CurrentWeather()
)