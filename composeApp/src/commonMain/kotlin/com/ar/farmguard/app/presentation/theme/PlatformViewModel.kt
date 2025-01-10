package com.ar.farmguard.app.presentation.theme

import androidx.lifecycle.ViewModel
import com.ar.farmguard.PlatformImpl

class PlatformViewModel(
    private val platform: PlatformImpl,
): ViewModel() {


    fun getPlatform() = platform.getPlatform()

}