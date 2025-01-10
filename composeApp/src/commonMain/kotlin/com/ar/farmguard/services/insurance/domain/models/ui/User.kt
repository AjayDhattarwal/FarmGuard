package com.ar.farmguard.services.insurance.domain.models.ui

data class User(
    val userName: String,
    val phoneNumber: String,
    val email: String? = null,
    val image: String? = null,
)
