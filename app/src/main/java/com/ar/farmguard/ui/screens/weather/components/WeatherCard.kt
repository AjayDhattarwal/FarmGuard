package com.ar.farmguard.ui.screens.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ar.farmguard.R
import com.ar.farmguard.utils.clickWithoutRipple

@Composable
fun WeatherCard(
    date: String,
    location: String,
    temperature: String,
    subtitle: String,
    weatherIcon: ImageVector,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .clickWithoutRipple{
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
                        text = "$location , $date",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = temperature,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Image(
                    imageVector = weatherIcon,
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .weight(1f)
                        .size(80.dp)
                        .align(Alignment.CenterVertically)
                )

            }
            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No spraying today - it's raining.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

        }

    }
}


@Preview
@Composable
fun WeatherCardPreview() {
    MaterialTheme {
        WeatherCard(
            date = "27 Nov 2023",
            location = "Thuian, Haryana",
            temperature = "25°C",
            subtitle = "Rainfall 12 mm",
            weatherIcon = ImageVector.vectorResource(id = R.drawable.cloudy)
        )
    }
}
