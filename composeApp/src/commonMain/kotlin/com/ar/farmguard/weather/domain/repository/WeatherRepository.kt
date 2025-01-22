package com.ar.farmguard.weather.domain.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.weather.domain.models.request.GeminiWeatherRequest
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.WeatherSummary
import com.ar.farmguard.weather.domain.models.response.WeatherResponse
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {

    val forecast: StateFlow<WeatherResponse?>

    suspend fun fetchWeather(coordinate: String): Result<WeatherResponse, DataError.Remote>

    suspend fun getLocationInfo(latitude: String, longitude: String, lang: String): Result<LocationInfo, DataError.Remote>

    suspend fun getWeatherNote(data: GeminiWeatherRequest): Result<WeatherSummary, DataError.Remote>

}