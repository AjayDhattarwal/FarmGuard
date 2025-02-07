package com.ar.farmguard.news.presentation.state_news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.core.presentation.shared.components.TopBar
import com.ar.farmguard.news.presentation.components.NewsCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StateNewsScreen(
    stateNewsViewModel: StateNewsViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit
) {
    val stateNews by stateNewsViewModel.stateNews.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                title = "State News",
                onBackPress = onBackPress
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            items(stateNews, key = { it.id }){
                NewsCard(
                    newsItem = it,
                    scaleImg = 1f,
                    onClick = navigate,
                    modifier = Modifier
                        .aspectRatio(3/2f)
                )
            }
            item{
                Spacer(Modifier.height(120.dp))
            }
        }
    }
}