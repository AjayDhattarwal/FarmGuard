package com.ar.farmguard.services.scheme.domain.model

data class SchemeDetailsState(
    val isLoading: Boolean = false,
    val schemeDetails: SchemeDetails = SchemeDetails(),
    val error: String? = null
)
