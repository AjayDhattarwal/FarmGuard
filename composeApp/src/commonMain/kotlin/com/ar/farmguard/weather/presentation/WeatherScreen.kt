package com.ar.farmguard.weather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton
import com.ar.farmguard.core.presentation.shared.components.NoteText
import com.ar.farmguard.core.presentation.shared.components.VerticalGridLayout
import com.ar.farmguard.weather.presentation.components.CelestialRiseAnimation
import com.ar.farmguard.weather.presentation.components.CurrentWeatherCard
import com.ar.farmguard.weather.presentation.components.HourlyWeatherCard
import com.ar.farmguard.weather.presentation.components.WeeklyWeatherCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = koinViewModel(),
) {

    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            ContentTitle(
                title = "Today's Weather",
                icon = {
                    IconThemeButton(
                        icon = Icons.Default.Settings,
                        contentDescription = "Settings"
                    ) {

                    }
                }
            ){
                Box(contentAlignment = Alignment.Center){

                    CurrentWeatherCard(
                        currentWeather = weatherState.currentWeather,
                        onClick = {},
                        astro = weatherState.currentForecast.astro,
                        isBlurEffect = weatherState.isLoading
                    )

                    if(weatherState.isLoading){
                        CircularProgressIndicator()
                    }
                }
            }


            NoteText(
                text = weatherState.weatherSummary?.note,
            )


            ContentTitle(
                title = "Hourly Forecast",
                space = 12.dp
            ){
                LazyRow {
                    items(weatherState.currentForecast.hour, key = { it.timeEpoch }){ forecast ->
                        HourlyWeatherCard(
                            forecastHour = forecast,
                            modifier = Modifier
                        )
                    }
                }

            }


            ContentTitle(
                title = "Astro",
                space = 12.dp
            ){

                VerticalGridLayout(
                    columns = 2,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier,
                ){
                    item{
                        CelestialRiseAnimation(
                            riseTime = weatherState.currentForecast.astro.sunrise,
                            setTime = weatherState.currentForecast.astro.sunset,
                            isSun = true,
                        )
                    }

                    item{
                        CelestialRiseAnimation(
                            riseTime = weatherState.currentForecast.astro.moonrise,
                            setTime = weatherState.currentForecast.astro.moonset,
                            isSun = false,
                        )
                    }
                }

            }


            ContentTitle(
                title = "Next 7 Days",
                space = 12.dp
            ) {
                VerticalGridLayout(
                    columns = 2,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier,
                ){
                    items(weatherState.forecast){ forecastDay ->
                        WeeklyWeatherCard(
                            forecastData = forecastDay
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(80.dp))
        }
    }
}

