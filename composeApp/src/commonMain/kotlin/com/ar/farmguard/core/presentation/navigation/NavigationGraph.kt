@file:OptIn(KoinExperimentalAPI::class)

package com.ar.farmguard.app.presentation.navigation

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ar.farmguard.home.HomeScreen
import com.ar.farmguard.marketprice.presentation.MarketPrice
import com.ar.farmguard.services.common.presentation.Services
import com.ar.farmguard.services.insurance.presentation.login.LoginScreen
import com.ar.farmguard.services.insurance.presentation.signup.components.AccountDetails
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.services.insurance.presentation.signup.components.FarmerDetailsForm
import com.ar.farmguard.services.insurance.presentation.signup.components.FarmerID
import com.ar.farmguard.services.insurance.presentation.signup.components.ResidentialDetails
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.AccountViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.ResidentialViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.SignUpViewModel
import com.ar.farmguard.weather.presentation.WeatherScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
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
            startDestination = SubGraph.SignUp,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Rtl),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
                )
        ) {

            signUpGraph(appController)

            homeGraph(appController)

            weatherGraph(appController)

            marketGraph(appController)

            servicesGraph(appController)



            composable<SubGraph.Settings> {
            }

            composable<SubGraph.Login> {
                LoginScreen(
                    navigate = appController::navigate
                )
            }


        }

    }

}




fun NavGraphBuilder.signUpGraph(
    appController: FarmGuardController,
){

    navigation<SubGraph.SignUp>(
        startDestination = SignUpDestination.FarmerDetails,
    ){
        composable<SignUpDestination.FarmerDetails> {
            val farmerViewModel: FarmerViewModel =
                it.sharedKoinViewModel(appController.navController)
            val signUpViewModel: SignUpViewModel =
                it.sharedKoinViewModel(appController.navController)

            FarmerDetailsForm(
                farmerViewModel = farmerViewModel,
                signUpViewModel = signUpViewModel,
                navigate = appController::navigate
            )
        }

        composable<SignUpDestination.ResidentialDetail> {
            val residentialViewModel: ResidentialViewModel =
                it.sharedKoinViewModel(appController.navController)

            ResidentialDetails(
                residentialViewModel = residentialViewModel,
                navigate = appController::navigate
            )

        }

        composable<SignUpDestination.FarmerID> {
            val farmerViewModel: FarmerViewModel =
                it.sharedKoinViewModel(appController.navController)

            FarmerID(
                farmerViewModel = farmerViewModel,
                navigate = appController::navigate
            )

        }

        composable<SignUpDestination.AccountDetails> {
            val accountViewModel: AccountViewModel =
                it.sharedKoinViewModel(appController.navController)

            AccountDetails(
                accountViewModel = accountViewModel,
                navigate = appController::navigate
            )

        }
    }
}


fun NavGraphBuilder.homeGraph(appController: FarmGuardController){
    navigation<SubGraph.Home>(
        startDestination = HomeDestination.Home
    ) {
        composable<HomeDestination.Home> {
            HomeScreen()
        }
    }
}


fun NavGraphBuilder.weatherGraph(appController: FarmGuardController){
    navigation<SubGraph.Weather>(
        startDestination = WeatherDestination.Weather
    ) {
        composable<WeatherDestination.Weather> {
            WeatherScreen()
        }
    }
}

fun NavGraphBuilder.marketGraph(appController: FarmGuardController){
    navigation<SubGraph.MarketPrice>(
        startDestination = MarketDestination.MarketPrice
    ) {
        composable<MarketDestination.MarketPrice> {
            MarketPrice()
        }
    }
}


fun NavGraphBuilder.servicesGraph(appController: FarmGuardController){
    navigation<SubGraph.Services>(
        startDestination = ServiceDestination.Services
    ) {
        composable<ServiceDestination.Services> {
            Services(){
                appController.navigate(SubGraph.SignUp)
            }
        }
    }
}





@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}

