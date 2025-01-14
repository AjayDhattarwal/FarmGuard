package com.ar.farmguard.services.insurance.calculator.domain.models.sssy

import kotlinx.serialization.Serializable

@Serializable
data class SssyResponse (
    var status : Boolean?        = null,
    var data   : List<SssyData> = emptyList(),
    var error  : String?         = null
)