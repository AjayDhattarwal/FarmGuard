package com.ar.farmguard.services.insurance.calculator.domain.models.district

import kotlinx.serialization.Serializable

@Serializable
data class PcDistrictResponse(
    var status : Boolean?       = null,
    var data   : DistrictData?  = null,
    var error  : String?        = null
)