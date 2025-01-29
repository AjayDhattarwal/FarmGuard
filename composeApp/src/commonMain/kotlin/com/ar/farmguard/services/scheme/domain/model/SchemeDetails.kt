package com.ar.farmguard.services.scheme.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchemeDetails(
    val pageProps: PageProps = PageProps(),
    @SerialName("__N_SSG")
    val nSsg: Boolean = false,
)

@Serializable
data class PageProps(
    val schemeData: SchemeDetailsData = SchemeDetailsData(),
    val schemeDataHi: SchemeDetailsDataHi = SchemeDetailsDataHi(),
    val faqs: Faqs = Faqs(),
    val faqsHi: FaqsHi = FaqsHi(),
    val docs: Docs = Docs(),
    val docsHi: DocsHi = DocsHi(),
)

@Serializable
data class SchemeDetailsData(
    @SerialName("_id")
    val id: String? = "",
    val en: En = En(),
    val slug: String? = "",
)

@Serializable
data class BasicDetails(
    val schemeOpenDate: String? = "",
    val schemeCloseDate: String? = null,
    val state: CommonData? = CommonData(),
    val nodalMinistryName: CommonData? = CommonData(),
    val nodalDepartmentName: CommonData? = CommonData(),
    val otherMinistryName: String? = null,
    val otherDepartmentNames: String? = null,
    val targetBeneficiaries: List<CommonData>? = emptyList(),
    val schemeSubCategory: List<CommonData>? = emptyList(),
    val dbtScheme: Boolean? = null,
    val implementingAgency: String? = "",
    val tags: List<String> = emptyList(),
    val schemeName: String = "",
    val schemeShortTitle: String? = "",
    val level: CommonData = CommonData(),
    val schemeCategory: List<CommonData> = emptyList(),
)

@Serializable
data class CommonData(
    val value: String? = "",
    val label: String? = ""
)

@Serializable
data class SchemeContent(
    val references: List<Reference> = emptyList(),
    val schemeImageUrl: String? = "",
    val briefDescription: String? = "",
    val detailedDescription: List<DetailedDescription> = emptyList(),
    val benefitTypes: CommonData = CommonData(),
    val benefits: List<Benefit> = emptyList(),
    @SerialName("benefits_md")  val benefitsMd: String? = null,
    @SerialName("detailedDescription_md") val detailedDescriptionMd: String? = null,
    @SerialName("exclusions_md") val exclusions : String? = null
)

@Serializable
data class Reference(
    val title: String? = "",
    val url: String? = "",
)

@Serializable
data class DetailedDescription(
    val type: String? = "",
    val children: List<Children> = emptyList(),
    val align: String? = "",
)


@Serializable
data class Benefit(
    val type: String? = "",
    val children: List<Children> = emptyList(),
)


@Serializable
data class ApplicationProcess(
    val mode: String? = "",
    val url: String? = "",
    val process: List<Process> = emptyList(),
)

@Serializable
data class Process(
    val type: String? = "",
    val children: List<Children> = emptyList(),
    val align: String? = "",
)



@Serializable
data class EligibilityCriteria(
    @SerialName("eligibilityDescription_md")
    val eligibilityDescriptionMd: String? = "",
    val eligibilityDescription: List<EligibilityDescription> = emptyList(),
)

@Serializable
data class EligibilityDescription(
    val type: String? = "",
    val children: List<Children> = emptyList(),
    val align: String? = "",
)


@Serializable
data class Children(
    val text: String? = "",
    val bold: Boolean? = null,
    val italic: Boolean? = null,
)


@Serializable
data class SchemeDetailsDataHi(
    @SerialName("_id")
    val id: String? = "",
    val slug: String? = "",
)

@Serializable
data class Faqs(
    val status: String? = "",
    val statusCode: Long = 0,
    val errorDescription: String? = "",
    val error: String? = "",
    val data: DetailsData = DetailsData(),
)


@Serializable
data class Faq(
    val question: String? = "",
    val answer: String? = "",
)

@Serializable
data class FaqsHi(
    val status: String? = "",
    val statusCode: Long = 0,
    val errorDescription: String? = "",
    val error: String? = "",
    val data: DetailsData = DetailsData(),
)

@Serializable
data class Docs(
    val status: String? = "",
    val statusCode: Long = 0,
    val errorDescription: String? = "",
    val error: String? = "",
    val data: DetailsData = DetailsData(),
)



@Serializable
data class DocumentsRequired(
    val type: String? = "",
    val children: List<Children> = emptyList(),
)



@Serializable
data class DocsHi(
    val status: String? = "",
    val statusCode: Long = 0,
    val errorDescription: String? = "",
    val error: String? = "",
    val data: DetailsData = DetailsData(),
)



@Serializable
data class DetailsData(
    @SerialName("_id")
    val id: String? = "",
    val en: En = En(),
    val schemeId: String? = "",
)



@Serializable
data class En(
    val basicDetails: BasicDetails = BasicDetails(),
    val schemeContent: SchemeContent = SchemeContent(),
    val applicationProcess: List<ApplicationProcess> = emptyList(),
    val eligibilityCriteria: EligibilityCriteria = EligibilityCriteria(),
    @SerialName("documents_required")
    val documentsRequired: List<DocumentsRequired> = emptyList(),
    val faqs: List<Faq> = emptyList(),
    @SerialName("documentsRequired_md") val documentsRequiredMd : String? = null
)
