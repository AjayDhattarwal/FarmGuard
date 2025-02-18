@file:OptIn(ExperimentalSharedTransitionApi::class
)

package com.ar.farmguard.core.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ar.farmguard.app.presentation.navigation.BottomNavigationBar
import com.ar.farmguard.app.presentation.navigation.FarmGuardController
import com.ar.farmguard.app.presentation.navigation.rememberFarmGuardController
import com.ar.farmguard.home.HomeScreen
import com.ar.farmguard.marketprice.presentation.SharedCommodityViewModel
import com.ar.farmguard.marketprice.presentation.commodity_details.CommodityDetails
import com.ar.farmguard.marketprice.presentation.market_home.MarketPrice
import com.ar.farmguard.news.presentation.news_details.NewsDetailsScreen
import com.ar.farmguard.news.presentation.state_news.StateNewsScreen
import com.ar.farmguard.services.common.presentation.Services
import com.ar.farmguard.services.insurance.CropInsuranceScreen
import com.ar.farmguard.services.insurance.applications.presentation.Application
import com.ar.farmguard.services.insurance.auth.presentation.login.LoginScreen
import com.ar.farmguard.services.insurance.auth.presentation.signup.components.AccountDetails
import com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.services.insurance.auth.presentation.signup.components.FarmerDetailsForm
import com.ar.farmguard.services.insurance.auth.presentation.signup.components.FarmerID
import com.ar.farmguard.services.insurance.auth.presentation.signup.components.ResidentialDetails
import com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel.AccountViewModel
import com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel.ResidentialViewModel
import com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel.SignUpViewModel
import com.ar.farmguard.services.insurance.calculator.presentation.PremiumCalculator
import com.ar.farmguard.services.insurance.status.presentation.ApplicationStatus
import com.ar.farmguard.services.scheme.presentation.SchemeDetails
import com.ar.farmguard.services.scheme.presentation.SchemeScreen
import com.ar.farmguard.weather.presentation.WeatherScreen
import org.koin.compose.viewmodel.koinViewModel

@OptIn( ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationGraph(){

    val appController = rememberFarmGuardController()


    Scaffold (
        bottomBar = {
            BottomNavigationBar(appController)
        }
    ) {
        SharedTransitionLayout {

            NavHost(
                navController = appController.navController,
                startDestination = SubGraph.Home,
                modifier = Modifier
            ) {

                signUpGraph(appController)

                homeGraph(appController)

                weatherGraph(appController)

                marketGraph(appController)

                servicesGraph(appController, this@SharedTransitionLayout)

                composable<SubGraph.Settings> {}



            }
        }

    }

}



fun NavGraphBuilder.homeGraph(appController: FarmGuardController){
    navigation<SubGraph.Home>(
        startDestination = HomeDestination.Home
    ) {
        composable<HomeDestination.Home> {
            HomeScreen(
                navigate = appController::navigate
            )
        }

        composable<HomeDestination.News> {

        }
        composable<HomeDestination.NewsDetails> {
            val news = it.toRoute<HomeDestination.NewsDetails>()

            NewsDetailsScreen(
                pathData = news,
                onBackPress = appController::upPress,
                navigate = appController::navigate
            )

        }

        composable<HomeDestination.StateNews> {
            StateNewsScreen(
                onBackPress = appController::upPress,
                navigate = appController::navigate
            )
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
            val sharedCommodityViewModel: SharedCommodityViewModel =
                it.sharedKoinViewModel(appController.navController)

            MarketPrice(
                navigateToDetails = { destination, commodity ->
                    sharedCommodityViewModel.setCommodity(commodity)
                    appController.navigate(destination)
                },
                navigate = appController::navigate
            )
        }

        composable<MarketDestination.CommodityDetails> {

            val commodity = it.toRoute<MarketDestination.CommodityDetails>()

            val sharedCommodityViewModel: SharedCommodityViewModel =
                it.sharedKoinViewModel(appController.navController)

            val selectedCommodity by sharedCommodityViewModel.selectedCommodity.collectAsStateWithLifecycle()

            Box(
                modifier = Modifier.fillMaxSize()
            ){
                selectedCommodity?.let {
                    CommodityDetails(
                        data = it,
                        navigate = appController::navigate,
                        onBackPress = appController::upPress
                    )
                }
            }


        }
    }
}

@ExperimentalSharedTransitionApi
fun NavGraphBuilder.servicesGraph(
    appController: FarmGuardController,
    sharedTransitionScope: SharedTransitionScope
){
    navigation<SubGraph.Services>(
        startDestination = ServiceDestination.Services
    ) {

        composable<ServiceDestination.Services> {
            Services(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope =  this@composable,
                navigate = appController::navigate,
            )
        }

        composable<ServiceDestination.Insurance> {
            CropInsuranceScreen(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope =  this@composable,
                isLoggedIn = true,
                farmerName = "Ajay Singh",
                navigate = appController::navigate,
                onBackPress = appController::upPress
            )
        }
        composable<ServiceDestination.Login> {
            val destination = it.toRoute<ServiceDestination.Login>().destination.toTargetKey()
            LoginScreen(
                navTarget = destination,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = this@composable,
                onBackPress = appController::upPress,
                navigate = appController::navigateWithPOP
            )
        }

        composable<ServiceDestination.InsuranceCalculator> {
            PremiumCalculator(
                onBackPress = appController::upPress,
                navigate = appController::navigate
            )
        }

        composable<ServiceDestination.ApplyInsurance> {  }

        composable<ServiceDestination.ApplicationStatus> {
            ApplicationStatus(
                navigate = appController::navigate,
                onBackPress = appController::upPress,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = this@composable
            )
        }

        composable<ServiceDestination.Applications> {
            Application(
                navigate = appController::navigate,
                onBackPress = appController::upPress,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = this@composable
            )
        }

        composable<ServiceDestination.Schemes> {
            SchemeScreen(
                onBackPress = appController::upPress,
                navigate = appController::navigate
            )
        }

        composable<ServiceDestination.SchemeDetails> {
            val id = it.toRoute<ServiceDestination.SchemeDetails>().id

            SchemeDetails(
                id = id,
                onBackPress = appController::upPress,
                navigate = appController::navigate
            )
        }


    }
}


fun NavGraphBuilder.signUpGraph(
    appController: FarmGuardController,
){

    navigation<ServiceDestination.SignUp>(
        startDestination = SignUpDestination.FarmerDetails,
    ){
        composable<SignUpDestination.FarmerDetails>(
            enterTransition = {
                fadeIn()
            },
            exitTransition ={
                slideOutHorizontally { -it }
            },
            popEnterTransition = {
                slideInHorizontally { -it }
            },
            popExitTransition ={
                slideOutHorizontally { it }
            }
        ){
            val farmerViewModel: FarmerViewModel =
                it.sharedKoinViewModel(appController.navController)
            val signUpViewModel: SignUpViewModel =
                it.sharedKoinViewModel(appController.navController)

            FarmerDetailsForm(
                farmerViewModel = farmerViewModel,
                signUpViewModel = signUpViewModel,
                navigate = appController::navigate,
                onBackPress = appController::upPress
            )
        }

        composable<SignUpDestination.ResidentialDetail>(
            enterTransition = {
                slideInHorizontally{ it }
            },
            exitTransition ={
                slideOutHorizontally { -it }
            },
            popEnterTransition = {
                slideInHorizontally { -it }
            },
            popExitTransition ={
                slideOutHorizontally { it }
            }
        ){
            val residentialViewModel: ResidentialViewModel =
                it.sharedKoinViewModel(appController.navController)

            ResidentialDetails(
                residentialViewModel = residentialViewModel,
                navigate = appController::navigate,
                onBackPress = appController::upPress
            )

        }

        composable<SignUpDestination.FarmerID>(
            enterTransition = {
                slideInHorizontally { it }
            },
            exitTransition ={
                slideOutHorizontally { -it }
            },
            popEnterTransition = {
                slideInHorizontally { -it }
            },
            popExitTransition ={
                slideOutHorizontally { it }
            }
        ) {
            val farmerViewModel: FarmerViewModel =
                it.sharedKoinViewModel(appController.navController)

            FarmerID(
                farmerViewModel = farmerViewModel,
                navigate = appController::navigate,
                onBackPress = appController::upPress

            )

        }

        composable<SignUpDestination.AccountDetails>(
            enterTransition = {
                slideInHorizontally { it }
            },
            exitTransition ={
                fadeOut()
            },
            popEnterTransition = {
                fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally { it }
            }
        ) {
            val accountViewModel: AccountViewModel =
                it.sharedKoinViewModel(appController.navController)

            AccountDetails(
                accountViewModel = accountViewModel,
                navigate = appController::navigate,
                onBackPress = appController::upPress
            )

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

