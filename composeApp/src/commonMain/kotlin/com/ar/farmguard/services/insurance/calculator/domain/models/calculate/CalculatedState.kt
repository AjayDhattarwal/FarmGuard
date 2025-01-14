package com.ar.farmguard.services.insurance.calculator.domain.models.calculate

import com.ar.farmguard.core.presentation.toTimeStamp

data class CalculatedState(
    val actuarialRate: String,
    val cutOfDate : String,
    val farmerShare: String,
    val sumInsuredPerHec: String,
    val crop: String,
    val insuranceCompanyName: String,
    val area: String,
    val premiumPaidByFarmer: String,
    val premiumPaidByGov: String,
    val totalSumInsured: String
)


fun PcData.toCalculatedState(area: Float, crop: String): CalculatedState{
    return CalculatedState(
        actuarialRate = "$premiumRate%",
        cutOfDate = cutOfDate.toTimeStamp().date.toString(),
        farmerShare = "$farmerShare%",
        sumInsuredPerHec = "₹$sumInsured",
        crop = crop,
        insuranceCompanyName = insuranceCompanyName,
        area = "$area / Hectare",
        premiumPaidByFarmer = "₹" +  (farmerShareValue * area).toString(),
        premiumPaidByGov = "₹" + ((goiShareValue + stateShareValue) * area).toString(),
        totalSumInsured = "₹" + (sumInsured * area).toString()

    )
}

