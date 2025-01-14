package com.ar.farmguard.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ar.farmguard.weather.domain.models.WeatherData
import com.ar.farmguard.marketprice.presentation.components.CropCard
import com.ar.farmguard.home.components.HomeTopBar
import com.ar.farmguard.home.components.NewsSection
import com.ar.farmguard.weather.presentation.components.CurrentWeatherCard
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.app.utils.TestViewmodel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    viewmodel: TestViewmodel = koinViewModel()
){

    val newsList = listOf(
        "Rain expected in northern regions tomorrow.",
        "Government announces new crop insurance scheme.",
        "Wheat prices increase by 10% this season.",
        "New subsidies available for solar pumps."
    )

    val userName by remember{ mutableStateOf("Ajay Singh") }


    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }



            item {
                HomeTopBar(
                    userName = userName,
                    profileImageUrl = "https://lh3.googleusercontent.com/a/ACg8ocKzOeHLR6u4PCmEh76OEwRuqybxt7t99S337pJaUKKmu3e3DgA=s576-c-no",
                    onSearchClicked = { },
                    onNotificationClicked = { }
                )
                Spacer(Modifier.height(10.dp))
            }



            item{
                ContentTitle(
                    title = "Today's Weather"
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
            }

            item {
                ContentTitle(
                    title = "Key Updates"
                ) {
                    newsList.forEach {
                        NewsSection(it) {}
                    }
                }
            }

            item {
                ContentTitle(
                    title = "Mandi Bhav Updates"
                ) {
                    CropCard(
                        imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjbmEswGo92XLT45FPzdIeqTy95f-GsO1jQFCsUjsaV5V-H_Gm2rV8zaEGJ7alEFRzpTMf7hCkV67oh2q4SkTCf2emwGx4kQBzukX-PfFwZ9XC1F-tOHJD1rvqGxlXHjPbari8a-TA71eLqCgkJhKdNxHoKNgwgAzqpsjJkrsNDmZUjLjJQoORJcLgC0E4/s200/PlaygroundImage4.heic",
                        cropName = "Wheat",
                        price = "₹100 / QTL",
                        marketName = "Market Name",
                        isPinned = true,
                        priceColor = Color(0xFF1E8805),
                        priceThread = "+₹20 (+13.33%)"
                    )
                }
            }

            item { Spacer(Modifier.height(120.dp)) }
        }
    }
}

@Preview
@Composable
fun HomeScreenPrev(){
    HomeScreen()
}
