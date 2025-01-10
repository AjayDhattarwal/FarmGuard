package com.ar.farmguard.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

object CreateDataStore{

    fun create(context: Context): DataStore<Preferences> = createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}