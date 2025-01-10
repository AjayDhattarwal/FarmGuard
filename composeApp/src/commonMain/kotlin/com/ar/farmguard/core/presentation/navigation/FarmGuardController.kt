package com.ar.farmguard.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun rememberFarmGuardController(
    navController: NavHostController = rememberNavController()
): FarmGuardController = remember(navController) {
    FarmGuardController(navController)
}



@Stable
class FarmGuardController(
    val navController: NavHostController,
) {

    fun upPress() {
        navController.navigateUp()
    }

    fun navigate(route: Any, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(route){
            }
        }
    }

    fun navigate(route: Any){
        navController.navigate(route)
    }

    fun navigateToBottomBarRoute(route: Any) {
        if (route::class.qualifiedName != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }


}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED


