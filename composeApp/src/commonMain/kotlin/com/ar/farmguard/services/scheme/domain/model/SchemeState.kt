package com.ar.farmguard.services.scheme.domain.model

data class SchemeState(
    val schemeResponse: SchemeResponse = SchemeResponse(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
    val items: List<SchemeItem> = emptyList()
)

