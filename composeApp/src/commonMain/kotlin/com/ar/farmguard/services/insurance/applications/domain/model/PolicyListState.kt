package com.ar.farmguard.services.insurance.applications.domain.model

import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message


data class PolicyListState(
    val list: List<UserPolicyData> = emptyList(),
    val isLoading: Boolean = false,
    val message: Message? = null
)
