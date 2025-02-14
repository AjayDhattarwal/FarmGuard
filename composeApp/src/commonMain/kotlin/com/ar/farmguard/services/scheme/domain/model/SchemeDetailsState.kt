package com.ar.farmguard.services.scheme.domain.model

data class SchemeDetailsState(
    val isLoading: Boolean = true,
    val schemeDetails: SchemeDetails = SchemeDetails(),
    val error: String? = null
)
