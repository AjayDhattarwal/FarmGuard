package com.ar.farmguard.weather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ar.farmguard.weather.domain.models.WeatherData
import com.ar.farmguard.weather.presentation.components.CurrentWeatherCard
import com.ar.farmguard.weather.presentation.components.WeeklyWeatherCard
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton
import com.ar.farmguard.core.presentation.shared.components.NoteText
import com.ar.farmguard.core.presentation.shared.components.VerticalGridLayout
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeatherScreen() {

    val upNextList = listOf(
        WeatherData(
            temperature = 25,
            humidity = 60,
            day = "Tuesday",
            image = ""
        ),
        WeatherData(
            temperature = 28,
            humidity = 70,
            day = "Wednesday",
            image = ""
            ),
        WeatherData(
            temperature = 24,
            humidity = 73,
            day = "Thursday",
            image = ""
        ),
        WeatherData(
            temperature = 22,
            humidity = 65,
            day = "Friday",
            image = ""
        )
    )

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {


            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            ContentTitle(
                title = "Today's Weather",
                icon = {
                    IconThemeButton(
                        icon = Icons.Default.Settings,
                        contentDescription = "Settings"
                    ) {
                        TODO()
                    }
                }
            ){
                CurrentWeatherCard(
                    weatherData = WeatherData(
                        temperature = 25,
                        humidity = 60,
                        description = "Partly cloudy with a chance of rain",
                        locationName = "Thuian, Haryana",
                        date = "28 Nov 2024"
                    ),
                    onClick = {}
                )
            }


            Spacer(modifier = Modifier.padding(8.dp))

            NoteText(
                text = "Sunny weather is expected over the weekend. A great time for outdoor harvesting activities."
            )

            Spacer(modifier = Modifier.padding(8.dp))

            ContentTitle(
                title = "Next 4 Days",
                space = 12.dp
            ) {
                VerticalGridLayout(
                    columns = 2,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier,
                ){
                    items(upNextList){
                        WeeklyWeatherCard(
                            weatherData = it
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(80.dp))
        }
    }
}

@Preview
@Composable
fun WeatherScreenPrev() {
    WeatherScreen()
}