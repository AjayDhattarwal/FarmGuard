package com.ar.farmguard.weather.domain.models.response

import com.ar.farmguard.weather.domain.models.request.GeminiUpNextData
import com.ar.farmguard.weather.domain.models.request.GeminiWeatherRequest
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastData
import kotlinx.serialization.Serializable

@Serializable
class WeatherResponse (
    val location : LocationInfo? = LocationInfo(),
    val current  : CurrentWeather?  = CurrentWeather(),
    val forecast : Forecast? = Forecast()
)


fun List<ForecastData>.toGeminiWeatherRequest(): GeminiWeatherRequest {
    return GeminiWeatherRequest(
        forecast = this.mapNotNull { it.toGeminiUpNextData() }
    )
}

fun ForecastData.toGeminiUpNextData(): GeminiUpNextData? {
    val data = GeminiUpNextData(
        date = date ?: return null,
        minTemp = day?.minTempC ?: return null,
        maxTemp = day.maxTempC,
        humidity = day.avgHumidity,
        windSpeed = day.maxWindKph,
        precipMm  = day.totalPrecipMm,
        condition = day.condition.text,
        willItRain = day.willItRain
    )

    return data
}