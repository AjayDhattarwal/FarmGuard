package com.ar.farmguard.weather.domain.network

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.home.domain.model.HomeWeatherResponse
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.WeatherResponse

interface WeatherApi  {
    suspend fun getForecast(coordinate: String): Result<WeatherResponse, DataError.Remote>

    suspend fun getLocationInfo(latitude: String, longitude: String, lang: String): Result<LocationInfo, DataError.Remote>

    suspend fun getCurrentWeather(
        coordinate: String
    ): Result<HomeWeatherResponse, DataError.Remote>

}