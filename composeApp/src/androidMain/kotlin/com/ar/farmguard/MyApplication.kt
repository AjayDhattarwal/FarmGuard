package com.ar.farmguard

import android.app.Application
import com.ar.farmguard.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MyApplication)

        }
    }
}