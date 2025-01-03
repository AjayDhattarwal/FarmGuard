package com.ar.farmguard.ui.screens.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun WeeklyWeatherCard(
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
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Box(contentAlignment = Alignment.Center){

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "${weatherData.day}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = rememberAsyncImagePainter(weatherData.image),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(70.dp)
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

            }
        }


    }
}

@Preview
@Composable
fun WeeklyWeatherCardPrev() {
    WeeklyWeatherCard(
        weatherData = WeatherData(
            temperature = 25,
            humidity = 60,
            day = "Tuesday",
            image = ""
        ),
    )
}


