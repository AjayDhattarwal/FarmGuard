package com.ar.farmguard.news.domian.model

import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message

data class NewsDetailsUiState(
    val newsDetails: NewsDetails? = null,
    val isLoading: Boolean = false,
    val message: Message? = null
)