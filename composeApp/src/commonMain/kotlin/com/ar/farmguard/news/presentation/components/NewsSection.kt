package com.ar.farmguard.news.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ar.farmguard.news.domian.model.NewsItem

@Composable
fun NewsSection(
    newsItems: List<NewsItem>,
    title: String,
    actionText: String = "See All",
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {},
    onItemClick: (Any) -> Unit = {}
) {

    Column(modifier = modifier) {
        if(newsItems.isNotEmpty()){
            SectionHeader(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = title,
                actionText = actionText,
                onActionClick = onActionClick
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "SpacerStart") {
                Spacer(Modifier.width(0.dp))
            }

            items(newsItems, key = { it.id }) { news ->
                NewsCard(newsItem = news, onClick = onItemClick)
            }
        }
    }
}
