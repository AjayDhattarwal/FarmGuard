package com.ar.farmguard.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ar.farmguard.app.utils.clickWithoutRipple
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_article
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun KeyUpdateItem(newsTitle: String, image: Any? = Res.drawable.ic_article,  modifier: Modifier = Modifier, onNewsClick: () -> Unit) {
    Card(
        modifier = modifier
            .clickWithoutRipple{
                onNewsClick()
            }
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (image != null) {
                AsyncImage(
                    model = image,
                    contentDescription = "News Image",
                    contentScale = ContentScale.Crop,
                    modifier =Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape),
                )
            }else {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_article),
                    contentDescription = "News Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = newsTitle,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview
@Composable
fun NewsTickerPre() {
    val newsList = listOf(
        "Rain expected in northern regions tomorrow.",
        "Government announces new crop insurance scheme.",
        "Wheat prices increase by 10% this season.",
        "New subsidies available for solar pumps."
    )

    Column(modifier = Modifier) {
        KeyUpdateItem(newsList.get(0)){}
    }
}
