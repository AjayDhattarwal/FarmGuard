package com.ar.farmguard.home.domain.model

import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.weather.domain.models.response.CurrentWeather
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastAstro

data class HomeState(
    val currentWeather: CurrentWeather = CurrentWeather(),
    val astro: ForecastAstro = ForecastAstro(),
    val isWeatherLoading: Boolean = false,
    val message: Message? = null,
    val location: LocationInfo = LocationInfo()
)
