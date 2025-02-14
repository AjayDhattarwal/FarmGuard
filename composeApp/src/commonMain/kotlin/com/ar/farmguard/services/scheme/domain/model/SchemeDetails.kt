package com.ar.farmguard.services.scheme.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchemeDetails(
    val pageProps: PageProps? = null,
    @SerialName("__N_SSG")
    val nSsg: Boolean? = false,
)

@Serializable
data class PageProps(
    val schemeData: SchemeDetailsData? = null,
    val faqs: Faqs? = null,
    val docs: Docs? = null,
)


@Serializable
data class Docs(
    val data: DocsData? = null
)

@Serializable
data class DocsData(
    @SerialName("_id")
    val id: String? = null,
    val en: DocsEN? = null
)

@Serializable
data class DocsEN(
    @SerialName("documents_required")
    val documentsRequired: List<ListCommon>? = null,
    val schemeId: String? = null
)


@Serializable
data class Faqs(
    val data: FaqData? = null
)

@Serializable
data class FaqData(
    @SerialName("_id")
    val id: String? = null,
    val en: FaqEN? = null
)

@Serializable
data class FaqEN(
    val faqs: List<FaqItem>? = null,
    val schemeId: String? = null
)

@Serializable
data class FaqItem(
    val question: String,
    val answer: String
)




@Serializable
data class SchemeDetailsData(
    @SerialName("_id")
    val id: String? = null,
    val en: SchemeEN? = null,
    val slug: String? = null,
)


@Serializable
data class SchemeEN(
    val basicDetails: BasicDetails? = null,
    val schemeContent: SchemeContent? = null,
    val applicationProcess: List<ApplicationProcess>? = null,
//    val schemeDefinitions: List<Common>? = null
    val eligibilityCriteria: EligibilityCriteria? = null,
    val applicationChannels: ApplicationChannels? = null,
)

@Serializable
data class BasicDetails(
    val schemeOpenDate: String? = null,
    val schemeCloseDate: String? = null,
    val nodalMinistryName: Common? = null,
    val nodalDepartmentName: Common? = null,
    val targetBeneficiaries: List<Common>? = emptyList(),
    val schemeSubCategory: List<Common>? = emptyList(),
    val dbtScheme: Boolean? = false,
    val implementingAgency: String? = null,
    val tags: List<String>? = null,
    val schemeName: String,
    val schemeShortTitle: String? = null,
    val level: Common? = null,
    val schemeType: Common? = null,
    val schemeCategory: List<Common>? = null
)


@Serializable
data class SchemeContent(
    val references: List<ReferenceItem>? = null,
    val schemeImageUrl: String? = null,
    val briefDescription: String? = null,
    val detailedDescription: List<ListCommon>? = null,
    val benefits: List<ListCommon>? = null,
    val exclusions: List<ListCommon>? = null,
)




@Serializable
data class ListCommon(
    val text: String? = null,
    val bold: Boolean = false,
    val type: String? = null,
    val link: String? = null,
    val children: List<ListCommon>? = null
)

@Serializable
data class ReferenceItem(
    val title: String,
    val url: String? = null
)



@Serializable
data class ApplicationProcess(
    val mode: String,
    val process: List<ListCommon> = emptyList()
)


@Serializable
data class EligibilityCriteria(
    val eligibilityDescription: List<ListCommon>? = null,
)





@Serializable
data class Common(
    val label: String,
    val value: String,
)



@Serializable
data class ApplicationChannels(
    val channels: List<Common> = emptyList()
)

