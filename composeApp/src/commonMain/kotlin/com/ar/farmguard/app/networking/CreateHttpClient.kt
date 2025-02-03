package com.ar.farmguard.app.networking

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import coil3.util.ServiceLoaderComponentRegistry.register
import com.ar.farmguard.app.utils.COOKIE_KEY
import com.ar.farmguard.app.utils.INSURANCE_DOMAIN
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.compression.ContentEncodingConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

object HttpClientFactory {

    private val cookiesKey = stringPreferencesKey(COOKIE_KEY)

    private var savedCookie: Cookie? = null

    fun create(engine: HttpClientEngine, dataStore: DataStore<Preferences>): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpCookies) {
                storage = object : CookiesStorage {
                    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
                        if (requestUrl.host == INSURANCE_DOMAIN) {
                            savedCookie = cookie
                            saveCookie(dataStore, cookieToString(cookie))

                        }
                    }

                    override suspend fun get(requestUrl: Url): List<Cookie> {
                        return if (requestUrl.host == INSURANCE_DOMAIN) {

                            val cookieString = savedCookie ?: getCookie(dataStore)?.parseToCookie()
                            savedCookie = cookieString
                            listOfNotNull(cookieString)

                        } else {
                            emptyList()
                        }
                    }


                    override fun close() {}
                }
            }
//            install(ContentEncoding) {
//                gzip()
//                deflate()
//            }
        }
    }




    private suspend fun getCookie(dataStore: DataStore<Preferences>): String? {
        val preferences = dataStore.data.first()
        return preferences[cookiesKey]
    }

    private suspend fun saveCookie(dataStore: DataStore<Preferences>, cookie: String) {
        dataStore.edit { preferences ->
            preferences[cookiesKey] = cookie
        }
    }
}




fun String.parseToCookie(): Cookie? {

    val cookieParts = this.split(";").map { it.trim() }

    val nameValue = cookieParts.first().split("=")
    if (nameValue.size != 2) return null

    val name = nameValue[0]
    val value = nameValue[1]

    var path = "/"
    val encoding = CookieEncoding.RAW

    cookieParts.drop(1).forEach { part ->
        when {
            part.startsWith("Path=") -> path = part.substringAfter("Path=")
        }
    }

    return Cookie(
        name = name,
        value = value,
        encoding = encoding,
        path = path,
        secure = true,
        httpOnly = true,
    )
}

fun cookieToString(cookie: Cookie): String {
    return buildString {
        append("${cookie.name}=${cookie.value}")
        append("; Path=${cookie.path}")
    }
}



