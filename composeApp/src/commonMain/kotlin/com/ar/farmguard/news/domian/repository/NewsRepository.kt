package com.ar.farmguard.news.domian.repository

import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.news.domian.model.NewsDetailResponse
import com.ar.farmguard.news.domian.model.NewsItem

interface NewsRepository{
    suspend fun getStateNews(state: String): Result<List<NewsItem>, DataError.Remote>
    suspend fun getNewsDetails(shortTag: String):Result<NewsDetailResponse, DataError.Remote>

}