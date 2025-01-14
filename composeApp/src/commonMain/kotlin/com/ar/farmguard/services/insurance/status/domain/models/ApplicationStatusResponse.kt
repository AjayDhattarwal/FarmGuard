package com.ar.farmguard.services.insurance.status.domain.models

import com.ar.farmguard.services.insurance.status.data.ApplicationStatusDto
import kotlinx.serialization.Serializable


@Serializable(with = ApplicationStatusDto::class)
data class ApplicationStatusResponse(
    val status: Boolean = false,
    val data: List<ApplicationStatusData> = emptyList(),
    val error: String = "",
    val level: String? = null
)


