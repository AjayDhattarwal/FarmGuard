package com.ar.farmguard.news.domian

import com.ar.farmguard.news.domian.model.NewsFeed
import com.ar.farmguard.news.domian.model.NewsItem
import com.ar.farmguard.news.domian.model.NewsResponse

fun NewsResponse.toNewsItemList(): List<NewsItem>{
    return feed.map { 
        it.toNewsItem()
    }
}

fun NewsFeed.toNewsItem(): NewsItem{
    return NewsItem(
        id = id,
        type = type,
        storyId = data.storyId,
        key = data.header.slug,
        modifiedTime =data.modifiedTime,
        title = data.header.title,
        source = data.category.nameEn,
        nameEn = data.category.nameEn,
        categoryId = data.category.id,
        image = data.header.media.firstOrNull()?.url ?: "",
        timestamp = data.publishTime,
        shortUrl = data.shortUrl.substringAfterLast("/").substringBeforeLast(".html")
    )
}