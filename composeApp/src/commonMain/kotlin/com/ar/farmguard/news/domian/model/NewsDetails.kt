package com.ar.farmguard.news.domian.model

data class NewsDetails(
    val storyId: Long,
    val templateType: String,
    val shareUri: String,
    val metaTitle: String,
    val metaDescription: String,
    val metaKeywords: String? = null,
    val time: String? = null,
    val videoPublishedTime: String? = null,
    val category: NewsCategory? = null,
    val location: NewsLocation? = null,
    val header: NewsHeader,
    val templateContent: List<TemplateContent> = emptyList(),
    val videoSummary: VideoSummary? = null,
    val relatedArticles: List<NewsItem> = emptyList(),
)
