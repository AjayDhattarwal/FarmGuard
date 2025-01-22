package com.ar.farmguard.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ar.farmguard.core.presentation.shared.components.ContentCard
import com.ar.farmguard.weather.domain.models.response.CurrentWeather
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastAstro
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_rain_chance
import farmguard.composeapp.generated.resources.ic_sunrise
import farmguard.composeapp.generated.resources.ic_sunset
import farmguard.composeapp.generated.resources.ic_wind_direction

@Composable
fun CurrentWeatherCard(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather,
    astro: ForecastAstro,
    onClick: () -> Unit = {},
    isBlurEffect: Boolean = false
) {


    ContentCard(
        modifier = modifier,
        onClick = onClick,
        isBlurEffect = isBlurEffect
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier

                ) {

                    Text(
                        text = currentWeather.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${currentWeather.tempC.toInt()}°C",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Humidity ${currentWeather.humidity}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(
                    Modifier
                        .weight(1f)
                        .width(1.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = currentWeather.image,
                        modifier = Modifier
                            .size(90.dp)
                            .offset {
                                IntOffset(0, -10)
                            },
                        contentDescription = "Weather Icon"
                    )
                    Text(
                        text = currentWeather.condition.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.offset {
                            IntOffset(0, -45)
                        }
                    )
                }

                Spacer(Modifier.width(8.dp))

            }
            currentWeather.feelsLikeC.let {

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LabeledIcon(
                        icon = Res.drawable.ic_sunrise,
                        label = astro.sunrise ?: ""
                    )

                    LabeledIcon(
                        icon = Res.drawable.ic_wind_direction,
                        label = currentWeather.windDir,
                        degree = currentWeather.windDegree
                    )

                    LabeledIcon(
                        icon = Res.drawable.ic_rain_chance,
                        label = "${ currentWeather.precipMm } mm"
                    )

                    LabeledIcon(
                        icon = Res.drawable.ic_sunset,
                        label = astro.sunset ?: ""
                    )
                }

            }

        }


    }
}


