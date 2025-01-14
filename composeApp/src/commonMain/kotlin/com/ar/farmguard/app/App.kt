package com.ar.farmguard.app

import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import coil3.util.DebugLogger
import com.ar.farmguard.core.presentation.navigation.NavigationGraph
import com.ar.farmguard.app.presentation.theme.FarmGuardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    FarmGuardTheme {
        NavigationGraph()
    }
}


fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.crossfade(true).logger(DebugLogger()).build()

