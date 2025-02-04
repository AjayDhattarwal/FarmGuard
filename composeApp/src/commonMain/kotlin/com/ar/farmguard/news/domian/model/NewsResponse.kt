package com.ar.farmguard.news.domian.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val categoryName: String,
    val metaTitle: String,
    val metaDescription: String,
    val metaKeywords: String,
    val cursor: String,
    val feed: List<NewsFeed>,
)

@Serializable
data class NewsFeed(
    val id: Long,
    val type: String,
    val data: NewsData,
)

@Serializable
data class NewsData(
    val storyId: Long,
    val shareUri: String,
    val templateType: String,
    val shortUrl: String,
    val publishTime: String,
    val modifiedTime: String,
    val category: NewsCategory,
    val header: NewsHeader,
    val articleLockType: String,
    val videoPublishedTime: String? = null,
    val blog: Blog? = null,
)

@Serializable
data class NewsCategory(
    val id: Long,
    val nameEn: String,
    val displayName: String,
    val color: String,
    val listingUrl: String,
    val img: String,
)

@Serializable
data class NewsHeader(
    val title: String,
    val slug: String? = null,
    val containsVideo: Boolean,
    val media: List<NewsMedia> = emptyList(),
    val tag: NewsTag? = null,
)

@Serializable
data class NewsTag(
    val text: String,
    val bgColorStart: String,
)


@Serializable
data class NewsMedia(
    val type: String,
    val url: String,
    val size: NewsMediaSize? = null,
    val thumbUrl: String? = null,
    val duration: Long? = null,
    val downloadUrl: String? = null,
)

@Serializable
data class NewsMediaSize(
    val w: Long? = null,
    val h: Long? = null,
)

@Serializable
data class Blog(
    val isLive: Boolean = false,
)
