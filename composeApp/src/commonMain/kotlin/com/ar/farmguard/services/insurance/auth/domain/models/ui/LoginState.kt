package com.ar.farmguard.services.insurance.auth.domain.models.ui

data class LoginState(
    val message: Message? = null,
    val isOtpRequested: Boolean = false,
    val isOtpVerifying: Boolean = false,
    val success: Boolean = false,
    val isLoading: Boolean = false
)
