package com.ar.farmguard.news.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ar.farmguard.app.utils.clickWithoutRipple
import com.ar.farmguard.core.presentation.navigation.HomeDestination
import com.ar.farmguard.core.presentation.shared.components.TimeChip
import com.ar.farmguard.news.domian.model.NewsItem

@Composable
fun NewsCard(
    newsItem: NewsItem,
    onClick: (Any) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(170.dp)
            .clickWithoutRipple{
                onClick(HomeDestination.NewsDetails)
            },
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = newsItem.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                            ),
                            startY = 100f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                SourceInfo(
                    source = newsItem.source,
                )

                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

           TimeChip(
                modifier = Modifier.padding(8.dp),
                timestamp = newsItem.relativeTime
            )

            IconButton(
                onClick = { /* Handle bookmark */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)

            ) {
                Icon(
                    imageVector = Icons.Outlined.Build,
                    contentDescription = "Bookmark",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}