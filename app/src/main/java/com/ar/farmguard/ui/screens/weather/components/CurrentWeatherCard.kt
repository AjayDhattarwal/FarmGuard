package com.ar.farmguard.ui.screens.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ar.farmguard.domain.model.WeatherData
import com.ar.farmguard.utils.clickWithoutRipple

@Composable
fun CurrentWeatherCard(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .clickWithoutRipple {
                onClick()
                TODO()
            }
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){

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
                        .width(1.dp))

                Image(
                    painter = rememberAsyncImagePainter(weatherData.image),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically)
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

@Preview
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
