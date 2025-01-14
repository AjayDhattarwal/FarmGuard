package com.ar.farmguard.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.readRawBytes
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestViewmodel(
    private val httpClient: HttpClient
) : ViewModel() {

    private val _svgData = MutableStateFlow(byteArrayOf())
    val svgData = _svgData.asStateFlow()

    val l = Logger.withTag("TestViewmodel")

    fun decrypt(){
        try{
            viewModelScope.launch {
                val response = httpClient.get("${IMG_BASE_URL}clear-day.svg"){
                    headers{
                        append(HttpHeaders.ContentType, "image/svg+xml")
                    }
                }
                _svgData.value = response.readRawBytes()

            }
        } catch (e: Exception){
            l.e(e){"error is this in the decrypt :${e.message}"}
        }
    }
}