package com.ar.farmguard.weather.domain.models.response

import com.ar.farmguard.app.utils.IMG_BASE_URL_WEATHER
import com.ar.farmguard.app.utils.weatherImageSelect
import com.ar.farmguard.core.presentation.formatTimestampToCustomString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    @SerialName("last_updated_epoch") val lastUpdatedEpoch: Long ? = null,
    @SerialName("last_updated") val lastUpdated: String = "",
    @SerialName("temp_c") val tempC: Float = 0.0001f,
    @SerialName("temp_f") val tempF: Float = 0.0f,
    @SerialName("is_day") val isDay: Int = 0,
    @SerialName("condition") val condition: Condition = Condition(),
    @SerialName("wind_mph") val windMph: Float = 0.0f,
    @SerialName("wind_kph") val windKph: Float = 0.0f,
    @SerialName("wind_degree") val windDegree: Float = 0.0f,
    @SerialName("wind_dir") val windDir: String = "",
    @SerialName("pressure_mb") val pressureMb: Float = 0.0f,
    @SerialName("pressure_in") val pressureIn: Float = 0.0f,
    @SerialName("precip_mm") val precipMm: Float = 0.0f,
    @SerialName("precip_in") val precipIn: Float = 0.0f,
    @SerialName("humidity") val humidity: Float = 0.0f,
    @SerialName("cloud") val cloud: Float = 0.0f,
    @SerialName("feelslike_c") val feelsLikeC: Float = 0.0f,
    @SerialName("feelslike_f") val feelsLikeF: Float = 0.0f,
    @SerialName("windchill_c") val windchillC: Float = 0.0f,
    @SerialName("windchill_f") val windchillF: Float = 0.0f,
    @SerialName("heatindex_c") val heatIndexC: Float = 0.0f,
    @SerialName("heatindex_f") val heatIndexF: Float = 0.0f,
    @SerialName("dewpoint_c") val dewPointC: Float = 0.0f,
    @SerialName("dewpoint_f") val dewPointF: Float = 0.0f,
    @SerialName("vis_km") val visKm: Float = 0.0f,
    @SerialName("vis_miles") val visMiles: Float = 0.0f,
    @SerialName("uv") val uv: Float = 0.0f,
    @SerialName("gust_mph") val gustMph: Float = 0.0f,
    @SerialName("gust_kph") val gustKph: Float = 0.0f
) {
    val image: String
        get() = weatherImageSelect(condition.icon ?: "")

    val date: String
        get() =  lastUpdatedEpoch?.let { formatTimestampToCustomString(it) } ?: lastUpdated
}
