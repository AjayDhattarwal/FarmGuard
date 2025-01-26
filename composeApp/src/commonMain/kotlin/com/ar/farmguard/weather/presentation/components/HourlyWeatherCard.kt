package com.ar.farmguard.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ar.farmguard.core.presentation.shared.components.ContentCard
import com.ar.farmguard.weather.domain.models.response.forecast.ForecastHour

@Composable
fun HourlyWeatherCard(forecastHour: ForecastHour, modifier: Modifier = Modifier) {
    
    ContentCard(
        onClick = {},
        modifier = modifier.padding(8.dp)
    ){
        Column( 
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            AsyncImage(
                model = forecastHour.image,
                contentDescription = "Weather Icon"
            )
            Text(
                text = forecastHour.timeString,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "${forecastHour.tempC}°C",
                style = MaterialTheme.typography.labelMedium
                    .copy(
                        fontWeight = FontWeight.Bold
                    )
            )
        }
    }
}