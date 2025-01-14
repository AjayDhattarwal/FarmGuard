package com.ar.farmguard.services.insurance.calculator.domain.models.district

import kotlinx.serialization.Serializable

@Serializable
data class DistrictHierarchy(
    var level3 : List<LevelDistrict> = emptyList()
)
