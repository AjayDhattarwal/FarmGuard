package com.ar.farmguard.weather.domain.models

import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.weather.domain.models.response.CurrentWeather
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.WeatherSummary
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastAstro
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastData

data class WeatherState(
    val currentWeather: CurrentWeather = CurrentWeather(),
    val forecast: List<ForecastData> = emptyList(),
    val location: LocationInfo = LocationInfo(),
    val isLoading: Boolean = false,
    val message: Message? = null,
    val astro: ForecastAstro = ForecastAstro(),
    val weatherSummary: WeatherSummary? = null,
    val isWeatherNoteLoading: Boolean = false
)

