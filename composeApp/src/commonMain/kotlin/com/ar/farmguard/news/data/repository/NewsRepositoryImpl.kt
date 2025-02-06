package com.ar.farmguard.news.data.repository

import co.touchlab.kermit.Logger
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.core.presentation.formatDateTimeFromString
import com.ar.farmguard.news.domian.model.BreakingNews
import com.ar.farmguard.news.domian.model.Headline
import com.ar.farmguard.news.domian.model.NewsDetailResponse
import com.ar.farmguard.news.domian.model.NewsDetails
import com.ar.farmguard.news.domian.model.NewsItem
import com.ar.farmguard.news.domian.model.RelatedArticle
import com.ar.farmguard.news.domian.network.NewsApi
import com.ar.farmguard.news.domian.repository.NewsRepository
import com.ar.farmguard.news.domian.toNewsItemList
import kotlin.random.Random

class NewsRepositoryImpl(
    private val newsApi: NewsApi
): NewsRepository {

    private val l = Logger.withTag("NewsRepository")

    override suspend fun getStateNews(
        state: String
    ): Result<List<NewsItem>, DataError.Remote> {
        val result = newsApi.getStateNews(state).map {
            it.toNewsItemList()
        }
        return result
    }

    override suspend fun getNewsDetails(shortTag: String): Result<NewsDetails, DataError.Remote> {
        return newsApi.getNewsDetails(shortTag).map {
            it.toNewsDetails()
        }
    }

    override suspend fun getBreakingNews(): Result<List<BreakingNews>, DataError.Remote> {
        return newsApi.getBreakingNews().map {
            parseHtmlContent(it.content)
        }
    }

    override suspend fun getTopHeadlines(): Result<List<Headline>, DataError.Remote> {
        return newsApi.getTopHeadlines().map{
            it.headlines
        }
    }


    private fun NewsDetailResponse.toNewsDetails(): NewsDetails{
        return NewsDetails(
            storyId = storyId,
            templateType = templateType,
            shareUri = shareUri,
            metaTitle = metaTitle,
            metaDescription = metaDescription,
            metaKeywords = metaKeywords,
            time = (modifiedTime ?: publishTime)?.let { formatDateTimeFromString(it) },
            videoPublishedTime = videoPublishedTime,
            category = category,
            location = location,
            header = header,
            templateContent = templateContent,
            videoSummary = videoSummary,
            relatedArticles = relatedArticles.map { it.toNewsItem() }
        )
    }

    private fun RelatedArticle.toNewsItem(): NewsItem{
        return NewsItem(
            id = storyId,
            title = header.title,
            slug = header.slug,
            source = category?.displayName ?: "",
            shortUrl = shortUrl.substringAfterLast("/").substringBeforeLast(".html"),
            timestamp = publishTime?: "",
            modifiedTime = modifiedTime?: "",
            nameEn = category?.nameEn,
            image = category?.img?: "",
            color = category?.color,
            categoryId = category?.id,
            type = ""
        )
    }

    private fun parseHtmlContent(html: String): List<BreakingNews> {

        val regex = """<h4>(.*?)<\/h4>\s*<p>(.*?)<\/p>""".toRegex()
        val res = regex.findAll(html)

        return res.map { matchResult ->
            val time = matchResult.groupValues[1]
            val text = matchResult.groupValues[2]
            BreakingNews(Random.nextInt(), title = text, time = time)
        }.toList()

    }
}


