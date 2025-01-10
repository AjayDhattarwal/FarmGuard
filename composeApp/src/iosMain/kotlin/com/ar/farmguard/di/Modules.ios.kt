package com.ar.farmguard.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ar.farmguard.PlatformSpec
import com.ar.farmguard.core.data.CreateDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    single <HttpClientEngine> { Darwin.create() }
    singleOf(::PlatformSpec)
    single <DataStore<Preferences>> { CreateDataStore.create()}
}