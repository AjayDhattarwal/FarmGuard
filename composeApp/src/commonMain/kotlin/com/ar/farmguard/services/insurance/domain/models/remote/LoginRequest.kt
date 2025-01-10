package com.ar.farmguard.services.insurance.domain.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val data: LoginData,
)

@Serializable
data class LoginData(
    val mobile: Long,
    val otp: Long
)

@Serializable
data class LoginResponse(
    val user: UserData
)

@Serializable
data class UserData(
    val status: Boolean,
    val error: String,
    val level: String,
)