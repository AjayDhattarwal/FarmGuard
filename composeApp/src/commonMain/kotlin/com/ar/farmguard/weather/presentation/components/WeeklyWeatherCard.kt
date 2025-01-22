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
import coil3.compose.AsyncImage
import com.ar.farmguard.core.presentation.shared.components.ContentCard
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastData

@Composable
fun WeeklyWeatherCard(
    modifier: Modifier = Modifier,
    forecastData: ForecastData,
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
                text = forecastData.dayOfWeek,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = forecastData.day?.image,
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .size(70.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${forecastData.day?.avgTempC?.toInt()}°C",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Humidity ${forecastData.day?.avgHumidity}%",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

        }


    }
}



