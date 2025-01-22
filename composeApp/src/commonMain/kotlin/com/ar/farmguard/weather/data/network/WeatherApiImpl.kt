package com.ar.farmguard.weather.data.network

import com.ar.farmguard.app.utils.ID
import com.ar.farmguard.app.utils.WEATHER_FORECAST_URL
import com.ar.farmguard.app.utils.WEATHER_LOCATION
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.home.domain.model.HomeWeatherResponse
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.WeatherResponse
import com.ar.farmguard.weather.domain.network.WeatherApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class WeatherApiImpl(
    private val httpClient: HttpClient
): WeatherApi {

    override suspend fun getForecast(
        coordinate: String
    ): Result<WeatherResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(WEATHER_FORECAST_URL){
                url {
                    parameters.append("q", coordinate)
                    parameters.append("days", "8")
                    parameters.append("key", ID)
                }
            }
        }
    }

    override suspend fun getLocationInfo(
        latitude: String,
        longitude: String,
        lang: String
    ): Result<LocationInfo, DataError.Remote> {
        return safeCall {
            httpClient.get(WEATHER_LOCATION){
                url {
                    parameters.append("format", "json")
                    parameters.append("accept-language", lang)
                    parameters.append("lon", longitude)
                    parameters.append("lat", latitude)
                }
            }
        }
    }

    override suspend fun getCurrentWeather(
        coordinate: String
    ): Result<HomeWeatherResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(WEATHER_FORECAST_URL){
                url {
                    parameters.append("q", coordinate)
                    parameters.append("key", ID)
                }
            }
        }
    }
}