package com.ar.farmguard.news.domian.network

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.news.domian.model.BreakingNewsResponse
import com.ar.farmguard.news.domian.model.Headline
import com.ar.farmguard.news.domian.model.HeadlineResponse
import com.ar.farmguard.news.domian.model.NewsDetailResponse
import com.ar.farmguard.news.domian.model.NewsResponse

interface NewsApi {
    suspend fun getStateNews(state: String): Result<NewsResponse, DataError.Remote>
    suspend fun getNewsDetails(shortTag: String): Result<NewsDetailResponse, DataError.Remote>
    suspend fun getBreakingNews(): Result<BreakingNewsResponse, DataError.Remote>
    suspend fun getTopHeadlines(): Result<HeadlineResponse, DataError.Remote>
}