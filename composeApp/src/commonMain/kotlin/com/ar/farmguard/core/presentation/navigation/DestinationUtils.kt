package com.ar.farmguard.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubGraph {

    @Serializable
    data object Home : SubGraph()

    @Serializable
    data object Settings : SubGraph()

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

    @Serializable
    data object News: HomeDestination()

    @Serializable
    data class NewsDetails(
        val shortTag: String,
        val title: String,
        val image: String,
    ) : HomeDestination()

    @Serializable
    data object StateNews: HomeDestination()
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

    @Serializable
    data class CommodityDetails(
        val commodity: String,
        val id: String,
    ) : MarketDestination()

}


sealed class ServiceDestination{

    @Serializable
    data object Services: ServiceDestination()

    @Serializable
    data object Insurance: ServiceDestination()

    @Serializable
    data object Schemes: ServiceDestination()

    @Serializable
    data class SchemeDetails(
        val id: String
    ): ServiceDestination()

    @Serializable
    data object Help: ServiceDestination()

    @Serializable
    data object Feedback: ServiceDestination()

    @Serializable
    data object InsuranceCalculator: ServiceDestination()

    @Serializable
    data object ApplicationStatus: ServiceDestination()

    @Serializable
    data object ApplyInsurance: ServiceDestination()

    @Serializable
    data object Applications: ServiceDestination()

    @Serializable
    data object SignUp : ServiceDestination()

    @Serializable
    data class Login(
        val destination: String = TargetKey.SERVICES.toString()
    ) : ServiceDestination()
}

@Serializable
enum class TargetKey {
    HOME,APPLICATIONS,APPLY_INSURANCE,SERVICES
}