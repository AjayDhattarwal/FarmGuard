package com.ar.farmguard.news.presentation.news_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ar.farmguard.core.presentation.navigation.HomeDestination
import com.ar.farmguard.marketprice.presentation.commodity_details.components.BlurredImageBackground
import com.ar.farmguard.news.domian.model.NewsDetails
import com.ar.farmguard.news.domian.model.TemplateContent
import com.ar.farmguard.news.presentation.components.NewsSection
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewsDetailsScreen(
    pathData: HomeDestination.NewsDetails,
    newsDetailsViewModel: NewsDetailsViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit
) {

    val newsState by newsDetailsViewModel.newsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        newsDetailsViewModel.fetchNewsDetails(pathData.shortTag)
    }
    BlurredImageBackground(
        onBackClick = onBackPress,
        imageUrl = pathData.image,
        isWithSubImage = false
    ){
        newsState.newsDetails?.let{

            NewsMetadata(it)

            NewsContent(it.templateContent)

            NewsSection(
                newsItems = it.relatedArticles,
                title = "Related News",
                onItemClick = navigate,
                scaleImg = 1.2f,
                onActionClick = { TODO() }
            )

        }
    }
}


@Composable
private fun NewsMetadata(newsData: NewsDetails) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = newsData.header.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = newsData.time ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Report: ${newsData.location?.text}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }


    }
}

@Composable
private fun NewsContent(contentItems: List<TemplateContent>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        contentItems.forEach { item ->
            when (item.type) {
                "paragraph" -> NewsParagraph(item)
                "image" -> NewsImage(item)
            }
        }
    }
}

@Composable
private fun NewsParagraph(item: TemplateContent) {
    val annotatedString = buildAnnotatedString {
        append(item.text)

        item.markups.forEach { markup ->
            addStyle(
                style = when (markup.mType) {
                    "bold" -> SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    "italic" -> SpanStyle(fontStyle = FontStyle.Italic)
                    "fontSize" -> SpanStyle(
                        fontSize = when (markup.value) {
                            "medium" -> 18.sp
                            else -> 16.sp
                        }
                    )

                    else -> SpanStyle(color = Color.Red)
                },
                start = if ((markup.start.toInt()) > (item.text?.length ?: 0)) {
                   (item.text?.length?.minus(1))?:0
                } else {
                    (markup.start.toInt())
                },
                end = if ((markup.end.toInt()) > (item.text?.length ?: 0))
                    (item.text?.length) ?:0
                 else
                    (markup.end.toInt())
            )
        }
    }
    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun NewsImage(item: TemplateContent) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        AsyncImage(
            model = item.url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Crop
        )

        if (!item.text.isNullOrEmpty()) {
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

