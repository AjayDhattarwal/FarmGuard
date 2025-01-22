package com.ar.farmguard.app.presentation.theme

import androidx.lifecycle.ViewModel
import com.ar.farmguard.PlatformSpec

class PlatformViewModel(
    private val platform: PlatformSpec
): ViewModel() {

    fun getPlatform() = platform.getPlatform()

}