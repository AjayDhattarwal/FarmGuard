package com.ar.farmguard.marketprice.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.ic_pin
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CropCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    cropName: String,
    price: String,
    marketName: String,
    isPinned: Boolean,
    priceThread: String,
    priceColor: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Crop Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cropName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = marketName,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Column {
                Text(
                    text = price,
                    style = MaterialTheme.typography.labelMedium,
                    color = priceColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = priceThread,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }



            if (isPinned) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_pin),
                    contentDescription = "Pinned",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.Top)
                        .graphicsLayer {
                            rotationZ = 45f
                            translationY = -15f
                            translationX = 15f
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun CropCardPrev(){
    CropCard(
        imageUrl = "https://example.com/image.jpg",
        cropName = "Wheat",
        price = "₹100 / QTL",
        marketName = "Market Name",
        isPinned = true,
        priceColor = Color(0xFF1E8805),
        priceThread = " +₹20 (+13.33%)"
    )
}