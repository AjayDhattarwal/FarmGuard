package com.ar.farmguard.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.ar.farmguard.weather.domain.models.WeatherData
import com.ar.farmguard.core.presentation.shared.components.ContentCard
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeeklyWeatherCard(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
    onClick: () -> Unit = {}
) {


    ContentCard(
        modifier = modifier,
        onClick = onClick,
        isGradient = false
    ){
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


