package com.ar.farmguard.services.insurance.auth.domain.models.ui

data class User(
    val userName: String,
    val phoneNumber: String,
    val email: String? = null,
    val image: String? = null,
)
