package com.ar.farmguard.news.data.network

import co.touchlab.kermit.Logger
import com.ar.farmguard.app.utils.BREAKING_NEWS
import com.ar.farmguard.app.utils.HEADLINE_NEWS
import com.ar.farmguard.app.utils.NEWS_DETAILS_BASE_URL
import com.ar.farmguard.app.utils.NEWS_KEY_String
import com.ar.farmguard.app.utils.NEWS_KEY_VALUE
import com.ar.farmguard.app.utils.NEWS_KEY_W_STRING
import com.ar.farmguard.app.utils.NEWS_KEY_W_VALUE
import com.ar.farmguard.app.utils.STATE_NEWS_BASE_URL
import com.ar.farmguard.core.data.safeCall
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.news.domian.model.BreakingNewsResponse
import com.ar.farmguard.news.domian.model.HeadlineResponse
import com.ar.farmguard.news.domian.model.NewsDetailResponse
import com.ar.farmguard.news.domian.model.NewsResponse
import com.ar.farmguard.news.domian.network.NewsApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

class NewsApiImpl (
    private val httpClient: HttpClient
): NewsApi{

    private val l = Logger.withTag("NewsApi")

    override suspend fun getStateNews(
        state: String
    ): Result<NewsResponse, DataError.Remote> {
        return safeCall(isDecrypt = false) {
            httpClient.get("$STATE_NEWS_BASE_URL/$state/"){
                headers {
                    append(NEWS_KEY_String, NEWS_KEY_VALUE)
                    append(HttpHeaders.AcceptEncoding, "gzip, deflate, br")
                }
            }
        }
    }

    override suspend fun getNewsDetails(shortTag: String): Result<NewsDetailResponse, DataError.Remote> {
        return safeCall(isDecrypt = false) {
            httpClient.get("$NEWS_DETAILS_BASE_URL/$shortTag") {
                headers {
                    append(HttpHeaders.AcceptEncoding, "gzip, deflate, br")
                    append(NEWS_KEY_W_STRING, NEWS_KEY_W_VALUE)
                    append("dtyp", "web")
                }
            }
        }
    }

    override suspend fun getBreakingNews(): Result<BreakingNewsResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(BREAKING_NEWS)
        }

    }

    override suspend fun getTopHeadlines(): Result<HeadlineResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(HEADLINE_NEWS)
        }
    }

}