package com.ar.farmguard.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ar.farmguard.weather.domain.models.WeatherData
import com.ar.farmguard.core.presentation.shared.components.ContentCard

@Composable
fun CurrentWeatherCard(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
    onClick: () -> Unit = {}
) {
    ContentCard(
        modifier = modifier,
        onClick = onClick,
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
                        text = "${weatherData.locationName} , ${weatherData.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${weatherData.temperature}℃",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Humidity ${weatherData.humidity}%",
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

                AsyncImage(
                    model = weatherData.image,
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterVertically),
                    contentDescription = "Weather Icon"
                )

                Spacer(Modifier.width(8.dp))

            }
            weatherData.description?.let {

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

        }


    }
}

@Composable
fun WeatherCardPreview() {
    MaterialTheme {
        CurrentWeatherCard(
            weatherData = WeatherData(
                temperature = 25,
                humidity = 60,
                description = "Partly cloudy with a chance of rain",
                locationName = "Thuian, Haryana",
                date = "28 Nov 2024"
            )
        )
    }
}
