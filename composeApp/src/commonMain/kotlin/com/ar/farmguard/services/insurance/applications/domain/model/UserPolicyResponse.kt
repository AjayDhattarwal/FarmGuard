package com.ar.farmguard.services.insurance.applications.domain.model

import com.ar.farmguard.services.insurance.applications.domain.dto.UserPolicySerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(UserPolicySerializer::class)
data class UserPolicyResponse(
    @SerialName("status" ) var status : Boolean? = null,
    @SerialName("data"   ) var data   : List<UserPolicyData> = emptyList(),
    @SerialName("error"  ) var error  : String?  = null,
    @SerialName("level"  ) var level  : String?  = null
)
