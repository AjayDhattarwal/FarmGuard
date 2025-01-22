package com.ar.farmguard.weather.domain.models.response

import kotlinx.serialization.Serializable

@Serializable
class WeatherSummary(
    val heading: String,
    val description: String,
    val note: String,
)