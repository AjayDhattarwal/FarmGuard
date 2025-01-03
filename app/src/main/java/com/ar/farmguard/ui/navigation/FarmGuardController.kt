package com.ar.farmguard.ui.navigation

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

    fun navigate(any: Any, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(any){
            }
        }
    }

    fun navigate(any: Any){
        navController.navigate(any)
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

//    fun navigateToHome(from: NavBackStackEntry) {
//
//        if (from.lifecycleIsResumed()) {
//            navController.navigate()
//        }
//    }

}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

