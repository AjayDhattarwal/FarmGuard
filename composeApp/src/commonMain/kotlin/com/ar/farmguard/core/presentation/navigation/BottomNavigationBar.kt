package com.ar.farmguard.app.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_handshake
import farmguard.composeapp.generated.resources.ic_handshake_filled
import farmguard.composeapp.generated.resources.ic_home
import farmguard.composeapp.generated.resources.ic_home_filled
import farmguard.composeapp.generated.resources.ic_mandi
import farmguard.composeapp.generated.resources.ic_mandi_filled
import farmguard.composeapp.generated.resources.ic_weather
import farmguard.composeapp.generated.resources.ic_weather_filled
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.reflect.KClass

@Composable
fun BottomNavigationBar(
    appState: FarmGuardController,
    modifier: Modifier = Modifier
) {
    val bottomScreens = remember {
        listOf(
            BottomNavItem.HomeObj,
            BottomNavItem.WeatherObj,
            BottomNavItem.MarketPriceObj,
            BottomNavItem.ServicesObj,
        )
    }

    val color = MaterialTheme.colorScheme.surface
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant

    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(color, color, color.copy(0.6f)),
                startY = Float.POSITIVE_INFINITY,
                endY = 0f
            )
        ),
    ) {


        bottomScreens.forEach { screen ->
            val currentDestination = navBackStackEntry?.destination
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(screen.obj::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = remember{ { appState.navigateToBottomBarRoute(screen.obj) } },
                icon = {
                    Icon(
                        imageVector =  vectorResource(if (isSelected) screen.selectedIcon else screen.unselectedIcon),
                        contentDescription = screen.label,
                    )
                },
                label = {
                    Text(
                        text = screen.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                modifier = Modifier.background(color = Color.Transparent),
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    selectedTextColor = selectedColor,
                    unselectedTextColor = unselectedColor,
                )
            )
        }
    }
}


sealed class BottomNavItem(val obj: Any, val unselectedIcon: DrawableResource, val selectedIcon: DrawableResource, val label: String) {
    data object HomeObj : BottomNavItem(SubGraph.Home, Res.drawable.ic_home, Res.drawable.ic_home_filled,  "Home")
    data object WeatherObj : BottomNavItem(SubGraph.Weather, Res.drawable.ic_weather, Res.drawable.ic_weather_filled, "Weather")
    data object MarketPriceObj : BottomNavItem(SubGraph.MarketPrice, Res.drawable.ic_mandi, Res.drawable.ic_mandi_filled ,"MandiBhav")
    data object ServicesObj: BottomNavItem(SubGraph.Services, Res.drawable.ic_handshake, Res.drawable.ic_handshake_filled,"Services")
}


