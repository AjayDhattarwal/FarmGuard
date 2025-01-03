package com.ar.farmguard.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ar.farmguard.R

@Composable
fun BottomNavigationBar(
    appState: FarmGuardController,
    modifier: Modifier = Modifier
) {
    val bottomScreens = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Search,
            BottomNavItem.Recognize,
            BottomNavItem.Library,
        )
    }

    val color = MaterialTheme.colorScheme.surfaceVariant
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant

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
        val navBackStackEntry = appState.navController.currentBackStackEntryAsState()


        bottomScreens.forEach { screen ->
            val currentDestination = navBackStackEntry.value?.destination
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == screen.obj::class.qualifiedName
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = remember{ { appState.navigateToBottomBarRoute(screen.obj) } },
                icon = {
                    Icon(
                        imageVector =  ImageVector.vectorResource(if (isSelected) screen.selectedIcon else screen.unselectedIcon),
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


sealed class BottomNavItem<T>(val obj: T, val unselectedIcon: Int, val selectedIcon: Int,  val label: String) {
    data object Home : BottomNavItem<AppDestination.Home>( AppDestination.Home, R.drawable.ic_home, R.drawable.ic_home_filled,  "Home")
    data object Search : BottomNavItem<AppDestination.Weather>( AppDestination.Weather, R.drawable.ic_weather,R.drawable.ic_weather_filled, "Weather")
    data object Recognize : BottomNavItem<AppDestination.MarketPrice>( AppDestination.MarketPrice, R.drawable.ic_mandi, R.drawable.ic_mandi_filled ,"MandiBhav")
    data object Library: BottomNavItem<AppDestination.Insurance>(AppDestination.Insurance, R.drawable.ic_insurance, R.drawable.ic_insurance_filled,"Insurance")
}


@Preview
@Composable
fun BottomNavigationBarPreview() {

    BottomNavigationBar(rememberFarmGuardController())

}