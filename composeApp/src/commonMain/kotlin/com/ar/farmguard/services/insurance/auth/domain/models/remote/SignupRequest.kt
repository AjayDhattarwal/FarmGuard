package com.ar.farmguard.services.insurance.auth.domain.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val captcha: String

)