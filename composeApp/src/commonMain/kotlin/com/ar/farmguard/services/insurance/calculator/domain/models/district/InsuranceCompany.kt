package com.ar.farmguard.services.insurance.calculator.domain.models.district

import kotlinx.serialization.Serializable

@Serializable
data class InsuranceCompany(
    var insuranceCompanyID   : String? = null,
    var insuranceCompanyCode : String? = null,
    var insuranceCompanyName : String? = null
)
