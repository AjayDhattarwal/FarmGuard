package com.ar.farmguard.news.presentation.news_details

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ar.farmguard.core.presentation.navigation.HomeDestination
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewsDetailsScreen(
    pathData: HomeDestination.NewsDetails,
    newsDetailsViewModel: NewsDetailsViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit
) {

    LaunchedEffect(Unit){
        newsDetailsViewModel.fetchNewsDetails(pathData.shortTag)
    }

    Scaffold {
        Text(
            text = pathData.shortTag
        )
        Text(
            text = pathData.title
        )
        Text(
            text = pathData.image ?: ""
        )
    }

}