package com.ar.farmguard.news.domian.model

import com.ar.farmguard.core.presentation.relativeTime

data class NewsItem(
    val id: Long,
    val type: String,
    val storyId: Long? = null,
    val key: String? = null,
    val modifiedTime: String,
    val title: String,
    val source: String,
    val nameEn: String? = null,
    val categoryId: Long? = null,
    val image: String,
    val timestamp: String,
    val slug: String? = null,
    val color: String? = null
){
    val relativeTime: String
        get() =  relativeTime(modifiedTime)
}