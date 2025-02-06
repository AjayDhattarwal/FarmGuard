package com.ar.farmguard.news.domian.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailResponse(
    val storyId: Long,
    val templateType: String,
    val shareUri: String,
    val metaTitle: String,
    val metaDescription: String,
    val metaKeywords: String? = null,
    val publishTime: String? = null,
    val modifiedTime: String? = null,
    val videoPublishedTime: String? = null,
    val category: NewsCategory? = null,
    val location: NewsLocation? = null,
    val header: NewsHeader ,
    val templateContent: List<TemplateContent> = emptyList(),
    val videoSummary: VideoSummary? = null,
    val relatedArticles: List<RelatedArticle> = emptyList(),
    val articleLockType: String? = null,
    val catFolder: String? = null,
)

@Serializable
data class NewsLocation(
    val id: Long,
    val text: String,
)

@Serializable
data class TemplateContent(
    val hash: String,
    val type: String,
    val text: String? = null,
    val markups: List<NewsMarkup> = emptyList(),
    val url: String? = null,
    val size: NewsMediaSize? = null,
    val isShareable: Boolean = false,
)

@Serializable
data class NewsMarkup(
    val start: Long,
    val end: Long,
    val mType: String,
    val value: String? = null,
    val url: String? = null,
    val target: String? = null,
)

@Serializable
data class VideoSummary(
    val type: String,
    val text: String,
    val url: String,
    val thumbUrl: String,
    val size: NewsMediaSize,
    val duration: Long,
    val downloadUrl: String,
)

@Serializable
data class RelatedArticle(
    val storyId: Long,
    val shareUri: String,
    val shortUrl: String,
    val publishTime: String? = null,
    val modifiedTime: String? = null,
    val videoPublishedTime: String? = null,
    val category: NewsCategory? = null,
    val header: NewsHeader,
    val articleLockType: String? = null,
    val podcast: Podcast? = null,
)

@Serializable
data class Podcast(
    val type: String,
    val url: String,
    val duration: Long? = null,
    val highlight: Boolean? = null,
)
