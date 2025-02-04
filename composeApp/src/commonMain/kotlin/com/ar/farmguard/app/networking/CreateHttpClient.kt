package com.ar.farmguard.app.networking

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import coil3.util.ServiceLoaderComponentRegistry.register
import com.ar.farmguard.app.utils.COOKIE_KEY
import com.ar.farmguard.app.utils.INSURANCE_DOMAIN
import com.ar.farmguard.core.data.decodeBrotli
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
import io.ktor.http.Headers
import io.ktor.http.Url
import io.ktor.http.content.OutgoingContent
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.ContentEncoder
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import io.ktor.utils.io.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

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
            install(ContentEncoding) {
                gzip()
                deflate()
                customEncoder(
                    encoder = object : ContentEncoder {
                        override val name: String = "br"
                        override fun decode(
                            source: ByteReadChannel,
                            coroutineContext: CoroutineContext
                        ): ByteReadChannel =  CoroutineScope(coroutineContext).run {
                            val byteArray = runBlocking { source.toByteArray() }
                            val decodedString = decodeBrotli(byteArray)
                            val byteReadChannel = decodedString.toByteReadChannel()
                            return byteReadChannel
                        }

                        override fun encode(
                            source: ByteReadChannel,
                            coroutineContext: CoroutineContext
                        ): ByteReadChannel = source

                        override fun encode(
                            source: ByteWriteChannel,
                           coroutineContext: CoroutineContext
                        ): ByteWriteChannel = source

                    },


                )
            }
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

fun String.toByteReadChannel(): ByteReadChannel {
    return ByteReadChannel(this.toByteArray(Charsets.UTF_8))
}


