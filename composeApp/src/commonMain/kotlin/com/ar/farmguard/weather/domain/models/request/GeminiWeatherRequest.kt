package com.ar.farmguard.weather.domain.models.request

import kotlinx.serialization.Serializable

@Serializable
data class GeminiWeatherRequest (
    val forecast : List<GeminiUpNextData> = emptyList()
)

@Serializable
data class GeminiUpNextData(
    val date: String,
    val minTemp: Float,
    val maxTemp: Float,
    val humidity: Float,
    val windSpeed: Float,
    val precipMm: Float,
    val condition: String,
    val willItRain: Int,
)