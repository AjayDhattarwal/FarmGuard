package com.ar.farmguard.news.domian.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeadlineResponse(
    val title: String,
    @SerialName("playlist")val headlines: List<Headline> = emptyList()
)