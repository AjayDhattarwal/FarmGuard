package com.ar.farmguard.services.insurance.calculator.domain.models.district

import kotlinx.serialization.Serializable

@Serializable
data class LevelDistrict(
    var level3ID         : String?           = null,
    var level3Name       : String?           = null,
    var level3           : String?           = null,
    var level3Code       : String?           = null,
    var insuranceCompany : InsuranceCompany? = InsuranceCompany()
)
