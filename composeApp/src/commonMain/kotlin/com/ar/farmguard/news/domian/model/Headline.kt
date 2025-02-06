package com.ar.farmguard.news.domian.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Headline(
    val id: Long,
    @SerialName("cont_url")val countUrl: String?= null,
    val title: String,
    val image: String? = null,
    val file : String? = null
)

