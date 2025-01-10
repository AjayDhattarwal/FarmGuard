package com.ar.farmguard.services.insurance.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import io.ktor.utils.io.core.toByteArray

@Composable
fun CaptchaSvgDisplay(modifier: Modifier = Modifier, svgData: () -> String ) {
    val context = LocalPlatformContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(svgData().toByteArray())
            .build(),
        contentDescription = "Captcha Image",
        imageLoader = imageLoader,
        modifier = modifier.background(Color.White)
    )
}
