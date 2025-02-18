package com.ar.farmguard.services.insurance.applications.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPolicyData(
     var villageName          : String? = null,
     var applicationNo        : String,
     var khataNo              : String?  = null,
     var landDivisonNumber    : String?  = null,
     var policyArea           : Double?  = null,
     var ratio                : String?  = null,
     var cropName             : String?  = null,
     var cropShare            : String?  = null,
     var sumInsured           : Double?  = null,
     var farmerShare          : Double?  = null,
     var policyStatusCode     : Int?     = null,
     var createdBy            : String?  = null,
     var totalPremium         : Double?  = null,
     var goiShare             : Double?  = null,
     var stateShare           : Double?  = null,
     var insuranceCompanyCode : String?  = null,
     var createdAt            : String?  = null,
     var insuranceCompanyName : String?  = null,
     var bankCscIcName        : String?  = null,
     var branchName           : String?  = null,
     var utrNumber            : String?  = null,
     var claimDate            : String?  = null,
     var applicationSource    : String?  = null,
     var claimStatus          : String?  = null,
     var claimAmount          : Double?  = null,
     var claimStatusCode      : Int?     = null,
     var accountNumber        : String?  = null,
     var claimType            : String?  = null,
     var retryPayment         : Boolean? = null,
     var isMix                : Int?     = null,
     var policyStatus         : String?  = null,
     var cutOfDate            : String?  = null
)
