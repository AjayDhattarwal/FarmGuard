package com.ar.farmguard

import androidx.compose.ui.window.ComposeUIViewController
import com.ar.farmguard.app.App
import com.ar.farmguard.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }