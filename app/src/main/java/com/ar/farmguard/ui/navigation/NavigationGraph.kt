package com.ar.farmguard.ui.navigation

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ar.farmguard.ui.screens.home.HomeScreen
import com.ar.farmguard.ui.screens.marketprice.MarketPrice
import com.ar.farmguard.ui.screens.weather.WeatherScreen

@Composable
fun NavigationGraph(){

    val appController = rememberFarmGuardController()

    Scaffold (
        bottomBar = {
            BottomNavigationBar(appController)
        }
    ) { innerPadding ->

        NavHost(
            navController = appController.navController,
            startDestination = AppDestination.Home,
            modifier = Modifier.padding(start = innerPadding.calculateStartPadding(LayoutDirection.Rtl),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr))
        ) {

            composable<AppDestination.Home> {
                HomeScreen()
            }

            composable<AppDestination.Weather> {
                WeatherScreen()
            }

            composable<AppDestination.MarketPrice> {
                MarketPrice()
            }

        }

    }

}