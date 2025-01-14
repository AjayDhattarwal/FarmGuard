package com.ar.farmguard.services.insurance.status.domain.models

import androidx.compose.ui.text.input.TextFieldValue

data class ApplicationStatusState(
    val data: List<ApplicationStatusData> = emptyList(),
    val captcha: TextFieldValue =  TextFieldValue(""),
    val policyId: TextFieldValue = TextFieldValue(""),
)



