package com.ar.farmguard.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ar.farmguard.PlatformSpec
import com.ar.farmguard.core.data.CreateDataStore
import com.ar.farmguard.core.data.LocationProviderImpl
import com.ar.farmguard.core.domain.location.LocationProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single <HttpClientEngine> { OkHttp.create() }
    singleOf(::PlatformSpec)
    single <DataStore<Preferences>> {CreateDataStore.create(get())}
    singleOf(::LocationProviderImpl).bind<LocationProvider>()
}