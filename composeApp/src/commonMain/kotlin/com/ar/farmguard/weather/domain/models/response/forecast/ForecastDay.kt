package com.ar.farmguard.weather.domain.models.response.forecast

import com.ar.farmguard.app.utils.weatherImageSelect
import com.ar.farmguard.weather.domain.models.response.Condition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ForecastDay(

    @SerialName("maxtemp_c"            ) val maxTempC          : Float = 0.0f,
    @SerialName("maxtemp_f"            ) val maxTempF          : Float = 0.0f,
    @SerialName("mintemp_c"            ) val minTempC          : Float = 0.0f,
    @SerialName("mintemp_f"            ) val minTempF          : Float = 0.0f,
    @SerialName("avgtemp_c"            ) val avgTempC          : Float = 0.0f,
    @SerialName("avgtemp_f"            ) val avgTempF          : Float = 0.0f,
    @SerialName("maxwind_mph"          ) val maxWindMph        : Float = 0.0f,
    @SerialName("maxwind_kph"          ) val maxWindKph        : Float = 0.0f,
    @SerialName("totalprecip_mm"       ) val totalPrecipMm     : Float = 0.0f,
    @SerialName("totalprecip_in"       ) val totalPrecipIn     : Float = 0.0f,
    @SerialName("totalsnow_cm"         ) val totalSnowCm       : Float = 0.0f,
    @SerialName("avgvis_km"            ) val avgVisKm          : Float = 0.0f,
    @SerialName("avgvis_miles"         ) val avgVisMiles       : Float = 0.0f,
    @SerialName("avghumidity"          ) val avgHumidity       : Float = 0.0f,
    @SerialName("daily_will_it_rain"   ) val willItRain   : Int = 0,
    @SerialName("daily_chance_of_rain" ) val dailyChanceOfRain : Int = 0,
    @SerialName("daily_will_it_snow"   ) val dailyWillItSnow   : Int = 0,
    @SerialName("daily_chance_of_snow" ) val dailyChanceOfSnow : Int = 0,
    @SerialName("condition"            ) val condition         : Condition = Condition(),
    @SerialName("uv"                   ) val uv                : Float = 0.0f

){
    val image: String
        get() = weatherImageSelect(condition.icon)
}
