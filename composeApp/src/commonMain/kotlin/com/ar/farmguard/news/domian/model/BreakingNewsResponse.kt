package com.ar.farmguard.news.domian.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreakingNewsResponse(
    @SerialName("html_content")val content: String
)