package com.ar.farmguard.marketprice.presentation.commodity_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.getDay
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.marketprice.domain.model.state.CommodityState
import com.ar.farmguard.marketprice.presentation.commodity_details.components.BlurredImageBackground
import com.ar.farmguard.marketprice.presentation.commodity_details.components.CropTradeHistoryCard
import com.ar.farmguard.marketprice.presentation.commodity_details.components.CropTrendGraph
import com.ar.farmguard.marketprice.presentation.commodity_details.components.GraphTypes


@Composable
fun CommodityDetails(
    data: CommodityState,
    navigate: (Any) -> Unit,
    onBackPress: () -> Unit
){

    val day  by remember { mutableStateOf(getDay(data.tradeData.first().createdAt)) }

    var selectedGraphType by remember { mutableStateOf(GraphTypes.MAX) }

    Scaffold {
        BlurredImageBackground(
            imageUrl = data.image,
            isPinned = false,
            onPinnedClick = {},
            onBackClick = onBackPress,
            modifier = Modifier,
            titleContent = {
                Spacer(Modifier.height(100.dp))
                Text(
                    text = data.commodity,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displaySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.offset {
                        IntOffset(-10, 0)
                    }
                )

                Text(
                    text = "${data.tradeData.first().state}, ${data.apmc}",
                    color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = day,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        )
                    )

                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = data.currentPriceThread,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(data.priceColor).copy(0.8f),
                        )
                    )
                }
            }
        ){

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                ContentTitle(
                    title = "Trend Graph"
                ){
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FilterChip(
                            selected = selectedGraphType == GraphTypes.MAX,
                            onClick = {
                                selectedGraphType = GraphTypes.MAX
                            },
                            label = {
                                Text(
                                    text = "Max Price",
                                )
                            }
                        )

                        FilterChip(
                            selected = selectedGraphType == GraphTypes.AVG,
                            onClick = {
                                selectedGraphType = GraphTypes.AVG
                            },
                            label = {
                                Text(
                                    text = "Avg Price",
                                )
                            }
                        )

                        FilterChip(
                            selected = selectedGraphType == GraphTypes.MIN,
                            onClick = {
                                selectedGraphType = GraphTypes.MIN
                            },
                            label = {
                                Text(
                                    text = "Min Price",
                                )
                            }
                        )
                    }

                    CropTrendGraph(
                        listOfTrade = data.tradeData,
                        graphType = selectedGraphType
                    )
                }

                ContentTitle(
                    title = "Last 7 days History"
                ){
                    data.tradeData.forEach { commodity ->
                        Spacer(Modifier.height(16.dp))
                        CropTradeHistoryCard(
                            price = commodity.maxPriceString,
                            date = commodity.createdAt,
                            minAvgPrice = commodity.minAvgPriceString,
                            priceColor = Color(commodity.priceColor),
                            priceThread = commodity.priceThreadString,
                            onCardClick = {}
                        )
                    }
                }
                Spacer(Modifier.height(120.dp))
            }

        }
    }

}




