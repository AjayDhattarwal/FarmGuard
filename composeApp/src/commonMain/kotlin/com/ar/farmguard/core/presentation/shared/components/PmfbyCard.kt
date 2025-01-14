package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ar.farmguard.app.utils.PMFBY_IMG

@Composable
fun PmfbyCard(
    modifier: Modifier = Modifier,
    imageSize: Dp = 150.dp,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    subTitleStyle: TextStyle = MaterialTheme.typography.labelMedium,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(PMFBY_IMG)
                .crossfade(true)
                .placeholderMemoryCacheKey("image-PMFBY0")
                .memoryCacheKey("image-PMFBY0")
                .build(),
            contentDescription = "PMFBY Logo",
            modifier = Modifier
                .size(imageSize)

        )
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Pradhan Mantri\nFasal Bima Yojana",
                style = titleStyle,
                lineHeight = MaterialTheme.typography.labelMedium.lineHeight,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Ministry of Agriculture and Farmers Welfare",
                style = subTitleStyle,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}