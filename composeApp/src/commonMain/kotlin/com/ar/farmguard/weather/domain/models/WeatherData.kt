package com.ar.farmguard.weather.domain.models

data class WeatherData(
    val temperature: Long,
    val day: String? = null,
    val feelsLike: Double? = null,
    val minTemperature: Double? = null,
    val maxTemperature: Double? = null,
    val humidity: Int = 0,
    val windSpeed: String? = null,
    val windDirection: Int? = null,
    val pressure: Int? = null,
    val uvIndex: Int? = null,
    val visibility: Int? = null,
    val precipitation: Double? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
    val condition: String = "",
    val description: String? = null,
    val image: String = "",
    val date: String = "",
    val locationName: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
)

