package com.ar.farmguard.marketprice.presentation.commodity_details.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.getDay

@Composable
fun CropTradeHistoryCard(
    modifier: Modifier = Modifier,
    date: String,
    price: String,
    minAvgPrice: String,
    priceThread: String,
    priceColor: Color,
    onCardClick: () -> Unit = {}
) {

    val day by remember{ mutableStateOf(getDay(date)) }

    Card(
        modifier = modifier,
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = minAvgPrice,
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
        }
    }
}
