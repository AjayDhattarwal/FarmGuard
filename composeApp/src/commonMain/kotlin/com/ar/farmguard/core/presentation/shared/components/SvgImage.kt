package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder

@Composable
fun SvgImage(modifier: Modifier = Modifier, svgData: ByteArray, contentDescription: String = "SVG Image" ) {
    val context = LocalPlatformContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(svgData)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier.background(Color.White)
    )
}
