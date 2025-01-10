package com.ar.farmguard

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

class AndroidPlatform(
    private val context: Context
) : Platform {
    private val isDarkMode = when (AppCompatDelegate.getDefaultNightMode()) {
        AppCompatDelegate.MODE_NIGHT_YES -> true
        AppCompatDelegate.MODE_NIGHT_NO -> false
        else -> {
            val uiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            uiMode == Configuration.UI_MODE_NIGHT_YES
        }
    }

    override val name: String = "Android"
    override val version: String = Build.VERSION.SDK_INT.toString()
    override val isDynamicTheme: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    override val colorScheme: ColorScheme? =  if(isDynamicTheme){
        if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else null

}


actual class PlatformSpec(
    private val context: Context
) {
    actual fun getPlatform(): Platform {
        return AndroidPlatform(context)
    }
}