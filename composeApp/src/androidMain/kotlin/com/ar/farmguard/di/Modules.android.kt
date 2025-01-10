package com.ar.farmguard.di

import com.ar.farmguard.PlatformSpec
import com.ar.farmguard.app.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    single {
        createHttpClient(OkHttp.create())
    }
    singleOf(::PlatformSpec)
}