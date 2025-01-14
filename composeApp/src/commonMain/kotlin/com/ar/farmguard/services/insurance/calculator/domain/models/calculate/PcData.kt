package com.ar.farmguard.services.insurance.calculator.domain.models.calculate

import kotlinx.serialization.Serializable

@Serializable
data class PcData(
    val indemnityLevel: Int,
    val premiumRate: Double,
    val farmerShare: Float,
    val farmerShareValue: Double,
    val goiShareValue: Double,
    val stateShareValue: Double,
    val insuranceCompanyName: String,
    val cutOfDate: Long,
    val sumInsured: Double,
    val stateShare: Double,
    val goiShare: Double,
    val tollFreeNumber: String,
    val headQuaterAddress: String,
    val headQuaterEmail: String,
    val websiteLink: String
)
