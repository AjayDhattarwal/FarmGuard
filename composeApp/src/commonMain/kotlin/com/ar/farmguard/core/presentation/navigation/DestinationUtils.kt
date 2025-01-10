package com.ar.farmguard.app.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubGraph {

    @Serializable
    data object Home : SubGraph()

    @Serializable
    data object Settings : SubGraph()

    @Serializable
    data object SignUp : SubGraph()

    @Serializable
    data object Login : SubGraph()

    @Serializable
    data object Weather : SubGraph()

    @Serializable
    data object MarketPrice : SubGraph()

    @Serializable
    data object Services: SubGraph()

}


sealed class HomeDestination {
    @Serializable
    data object Home : HomeDestination()
}


sealed class SignUpDestination {

    @Serializable
    data object FarmerDetails : SignUpDestination()

    @Serializable
    data object ResidentialDetail : SignUpDestination()

    @Serializable
    data object FarmerID : SignUpDestination()

    @Serializable
    data object AccountDetails : SignUpDestination()

}


sealed class WeatherDestination {

   @Serializable
   data object Weather : WeatherDestination()

}

sealed class MarketDestination {

    @Serializable
    data object MarketPrice : MarketDestination()

}

sealed class ServiceDestination{

    @Serializable
    data object Services: ServiceDestination()

    @Serializable
    data object Insurance: ServiceDestination()

    @Serializable
    data object Schemes: ServiceDestination()

    @Serializable
    data object Help: ServiceDestination()

    @Serializable
    data object Feedback: ServiceDestination()
}