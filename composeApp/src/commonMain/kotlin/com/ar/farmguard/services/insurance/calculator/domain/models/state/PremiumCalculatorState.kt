package com.ar.farmguard.services.insurance.calculator.domain.models.state

data class PremiumCalculatorState(
    val isLoading: Boolean = false,
    val season: String = "",
    val year: String = "",
    val scheme: String = "",
    val state: String = "",
    val district: String = "",
    val crop: String = "",
    val area: String = "",
)
