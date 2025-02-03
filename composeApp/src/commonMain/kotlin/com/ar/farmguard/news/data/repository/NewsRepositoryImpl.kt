package com.ar.farmguard.news.data.repository

import co.touchlab.kermit.Logger
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.map
import com.ar.farmguard.news.domian.model.NewsItem
import com.ar.farmguard.news.domian.network.NewsApi
import com.ar.farmguard.news.domian.repository.NewsRepository
import com.ar.farmguard.news.domian.toNewsItemList

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
        l.e("result: $result")
        return result
    }
}