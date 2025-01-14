package com.ar.farmguard.services.insurance.calculator.domain.models.sssy

import kotlinx.serialization.Serializable



@Serializable
data class SssyName (
    var schemeName      : String? = null,
    var seasonName      : String? = null,
    var stateName       : String? = null,
    var schemeNameShort : String? = null,
    var year            : String? = null
)