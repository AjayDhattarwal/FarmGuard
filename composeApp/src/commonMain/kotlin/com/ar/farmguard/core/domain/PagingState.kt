package com.ar.farmguard.core.domain

data class PagingState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)