package com.ar.farmguard.services.insurance.calculator.domain.models.sssy

import kotlinx.serialization.Serializable

@Serializable
data class SssyData (
    var sssyID                        : String?   = null,
    var seasonID                      : String?   = null,
    var seasonCode                    : String?   = null,
    var schemeID                      : String?   = null,
    var schemeCode                    : String?   = null,
    var stateID                       : String?   = null,
    var stateCode                     : String?   = null,
    var sssyName                      : SssyName? = SssyName(),
    var year                          : String?   = null,
    var startDate                     : Int?      = null,
    var endDate                       : Int?      = null,
    var policyStartDate               : Int?      = null,
    var policyEndDate                 : Int?      = null,
    var bankLoaneeEndDate             : Int?      = null,
    var bankNonloaneeEndDate          : Int?      = null,
    var isOpen                        : Int?      = null,
    var cnStarted                     : Int?      = null,
    var cnIsCopied                    : String?   = null,
    var policy                        : Int?      = null,
    var notification                  : Int?      = null,
    var isPreviousSeasonYearInSubsidy : Boolean?  = null,
    var isOfflineChallan              : Boolean?  = null,
    var firstGoiSubsidy               : Boolean?  = null,
    var goiOfflineChallan             : Boolean?  = null,
    var stateOfflineChallan           : Boolean?  = null,
    var ayTy                          : Int?      = null,
    var yieldEndDate                  : Int?      = null,
    var currentTime                   : Int?      = null,
    var currentHour                   : Int?      = null,
    var addonStartDate                : String?   = null,
    var addonEndDate                  : String?   = null,
    var default                       : Int?      = null

)