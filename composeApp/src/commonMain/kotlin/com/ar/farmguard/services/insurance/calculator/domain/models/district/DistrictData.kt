package com.ar.farmguard.services.insurance.calculator.domain.models.district

import kotlinx.serialization.Serializable

@Serializable
data class DistrictData(
    var levels    : List<Int> = emptyList(),
    var nextIndex : Int?           = null,
    var offset    : Int?           = null,
    var hierarchy : DistrictHierarchy?  = null
)
