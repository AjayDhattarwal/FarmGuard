package com.ar.farmguard

import androidx.compose.material3.ColorScheme
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName()
    override val version: String = UIDevice.currentDevice.systemVersion
    override val isDynamicTheme: Boolean = false
    override val colorScheme: ColorScheme? = null
}


actual class PlatformSpec() {
    actual fun getPlatform(): Platform {
        return IOSPlatform()
    }
}