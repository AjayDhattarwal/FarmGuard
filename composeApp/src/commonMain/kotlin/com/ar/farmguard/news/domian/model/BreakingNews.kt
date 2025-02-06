package com.ar.farmguard.news.domian.model

data class BreakingNews(
    val id: Int,
    val title: String,
    val url: String? = null,
    val time: String
)

