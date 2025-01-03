package com.ar.farmguard.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppDestination {
    @Serializable
    data object Home : AppDestination()

    @Serializable
    data object Weather: AppDestination()

    @Serializable
    data object CropRates: AppDestination()

    @Serializable
    data object Insurance: AppDestination()


//
//
//    @Serializable
//    data object Settings : AppDestination()
//
//    @Serializable
//    data object About : AppDestination()
//
//    @Serializable
//    data object HelpAndFeedback : AppDestination()
//
//    @Serializable
//    data object Privacy : AppDestination()
//
//    @Serializable
//    data object TermsAndConditions : AppDestination()
//
//    @Serializable
//    data object License : AppDestination()
//
//    @Serializable
//    data object AboutApp : AppDestination()
//
//    @Serializable
//    data object AboutDeveloper : AppDestination()
//
//    @Serializable
//    data object Personalization: AppDestination()
//
//    @Serializable
//    data object Notifications: AppDestination()
//
//
//
//    @Serializable
//    data object SearchSettings: AppDestination()
//
//
//
//    @Serializable
//    data object Language: AppDestination()
//
//
//    @Serializable
//    data object Accessibility: AppDestination()
//
//    @Serializable
//    data object Widget: AppDestination()
//
//    @Serializable
//    data object PrivacyPolicy: AppDestination()



}