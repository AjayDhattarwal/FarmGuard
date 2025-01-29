package com.ar.farmguard.services.scheme.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SchemeResponse(
    val status           : String?      = null,
    val statusCode       : Int?         = null,
    val errorDescription : String?      = null,
    val error            : String?      = null,
    val data             : SchemeData  = SchemeData()
)

@Serializable
data class SchemeData(
    val summary  : SchemeSummary           = SchemeSummary(),
    val facets   : ArrayList<SchemeFacet>   = arrayListOf(),
    val hits     : SchemeHits              = SchemeHits(),
    val sortedBy : String?                  = null
)

@Serializable
data class SchemeSummary(
    val total           : Int?                      = null,
    val query           : String?                   = null,
    val sortOptions     : List<SortOption>          = emptyList(),
    val appliedFilters  : List<AppliedFilter>       = emptyList(),
    val disabledFilters : List<String>              = emptyList()
)

@Serializable
data class SortOption(
    val id    : String? = null,
    val label : String? = null
)

@Serializable
data class AppliedFilter(
    val identifier : String? = null,
    val id         : String? = null,
    val label      : String? = null,
    val display    : String? = null,
    val type       : String? = null,
    val value      : String? = null
)

@Serializable
data class SchemeFacet(
    val identifier : String?            = null,
    val label      : String?            = null,
    val display    : String?            = null,
    val type       : String?            = null,
    val entries    : List<SchemeEntry> = emptyList()
)

@Serializable
data class SchemeEntry(
    val label: String? = null,
    val count: Long? = null,
)

@Serializable
data class SchemeHits(
    val items : List<SchemeItem> = emptyList(),
    val page  : SchemePage?      = SchemePage()
)

@Serializable
data class SchemeItem(
    val id        : String,
    val fields    : Fields    = Fields(),
)

@Serializable
data class Fields(
    val beneficiaryState  : List<String>      = emptyList(),
    val schemeShortTitle  : String?           = null,
    val level             : String?           = null,
    val nodalMinistryName : String?           = null,
    val schemeCategory    : List<String>      = emptyList(),
    val schemeName        : String?           = null,
    val schemeCloseDate   : String?           = null,
    val slug              : String?           = null,
    val briefDescription  : String?           = null,
    val age               : SchemeAge?        = SchemeAge(),
    val tags              : List<String>      = emptyList()
)


@Serializable
data class SchemeAge(
    val ews: AgeData        = AgeData(),
    val sc: AgeData         = AgeData(),
    val general: AgeData    = AgeData(),
    val obc: AgeData        = AgeData(),
    val st: AgeData         = AgeData(),
    val female: AgeData     = AgeData(),
    val widowed: AgeData    = AgeData()
)

@Serializable
data class AgeData(
    val gte : Int? = null,
    val lte : Int? = null,
)

@Serializable
data class SchemePage(
    val total      : Int? = null,
    val totalPages : Int? = null,
    val pageNumber : Int? = null,
    val from       : Int? = null,
    val size       : Int? = null
)

@Serializable
data class Page (

    var total      : Int? = null,
    var totalPages : Int? = null,
    var pageNumber : Int? = null,
    var from       : Int? = null,
    var size       : Int? = null

)