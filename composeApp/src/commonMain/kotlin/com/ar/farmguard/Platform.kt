package com.ar.farmguard

import androidx.compose.material3.ColorScheme

interface Platform {
    val name: String
    val version: String
    val isDynamicTheme: Boolean
    val colorScheme: ColorScheme?
}

expect class PlatformSpec {
    fun getPlatform(): Platform
}


