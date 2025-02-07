package com.ar.farmguard.news.domian.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.news.domian.model.BreakingNews
import com.ar.farmguard.news.domian.model.Headline
import com.ar.farmguard.news.domian.model.NewsDetails
import com.ar.farmguard.news.domian.model.NewsItem
import kotlinx.coroutines.flow.StateFlow

interface NewsRepository{
    val stateNews: StateFlow<List<NewsItem>>
    suspend fun getStateNews(state: String): Result<List<NewsItem>, DataError.Remote>
    suspend fun getNewsDetails(shortTag: String):Result<NewsDetails, DataError.Remote>
    suspend fun getBreakingNews():Result<List<BreakingNews>, DataError.Remote>
    suspend fun getTopHeadlines(): Result<List<Headline>, DataError.Remote>
}