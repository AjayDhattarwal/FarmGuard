package com.ar.farmguard.services.insurance.status.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationStatusData(
    val policyStatus: Long,
    val status: Long,
    val source: String,
    val cropName: String,
    val villageName: String,
    val farmerName: String,
    val applicationNo: String,
    @SerialName("policyID") val policyId: String,
    val level3Name: String,
    val createdAt: String,
    val stateName: String,
    val message: String,
    val year: String,
    val seasons: String,
    val scheme: String,
)
