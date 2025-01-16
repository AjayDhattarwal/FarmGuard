@file:OptIn(ExperimentalFoundationApi::class)

package com.ar.farmguard.marketprice.presentation.market_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.navigation.MarketDestination
import com.ar.farmguard.marketprice.presentation.market_home.components.CropCard
import com.ar.farmguard.core.presentation.shared.components.AnimatedSearchBar
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton
import com.ar.farmguard.marketprice.domain.model.state.CommodityState
import com.ar.farmguard.services.insurance.auth.signup.components.SelectorField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MarketPrice(
    marketViewModel: MarketCommodityViewModel = koinViewModel(),
    navigateToDetails: (Any, CommodityState) -> Unit
){

    val lazyColumnState = rememberLazyListState()

    val state by marketViewModel.state.collectAsState()
    val userInfo by marketViewModel.userDataState.collectAsState()
    val searchQuery by marketViewModel.searchQuery.collectAsState()

    val isLoading by marketViewModel.isLoading.collectAsState()

    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = lazyColumnState,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }

            item(key = "title") {
                ContentTitle(
                    title = "Market Price",
                    icon = {
                        IconThemeButton(
                            icon = Icons.Default.MoreVert,
                            contentDescription = "More"
                        ) { }
                    }
                )
            }


            if(isLoading) {
                item {
                    Spacer(Modifier.height(180.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else{

                if (state.isUserDataAvailable) {
                    item(key ="SearchBar"){
                        AnimatedSearchBar(
                            value = { searchQuery },
                            onValueChange = marketViewModel::updateSearchQuery,
                            lazyState = lazyColumnState
                        )
                    }
                }

                if (!state.isUserDataAvailable) {
                    item {
                        Spacer(Modifier.height(100.dp))
                    }
                    item(key = "select state") {
                        SelectorField(
                            options = userInfo.stateSelectionList,
                            selectedOption = userInfo.state,
                            onOptionSelected = {
                                marketViewModel.saveUserInfo("state", it)
                            },
                            placeholder = "Select State",
                            readOnly = false

                        )
                    }

                    item (key = "select apmc"){
                        SelectorField(
                            options = userInfo.apmcSelectionList,
                            selectedOption = userInfo.apmc,
                            onOptionSelected = {
                                marketViewModel.saveUserInfo("apmc", it)
                            },
                            placeholder = "Select APMC (Mandi)",
                            readOnly = userInfo.state.isEmpty()
                        )
                    }

                    item (key = "select language"){
                        SelectorField(
                            options = listOf("English", "Hindi"),
                            selectedOption = userInfo.language,
                            onOptionSelected = {
                                marketViewModel.saveUserInfo("language", it)
                            },
                            placeholder = "Select Language",
                            readOnly = userInfo.apmc.isEmpty()

                        )
                    }

                    item (key = "save user") {
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                marketViewModel.saveUserInfo("submit", "")
                            }
                        ){
                            Text("Save")
                        }
                    }
                }

                items(state.commodityWithHistory, key = { crop -> crop.commodity }) { commodity ->
                    Box(
                        modifier = Modifier.animateItem()
                    ) {
                        Spacer(Modifier.height(8.dp))

                        val latestData = commodity.tradeData.first()

                        CropCard(
                            imageUrl = commodity.image,
                            cropName = commodity.commodity,
                            price = "₹${latestData.maxPrice} / QTL",
                            marketName = latestData.apmc,
                            isPinned = false,
                            priceColor = Color(commodity.priceColor),
                            priceThread = commodity.currentPriceThread,
                            onCardClick = {
                                navigateToDetails(
                                    MarketDestination.CommodityDetails(
                                        commodity.commodity,
                                        latestData.id
                                    ),
                                    commodity
                                )
                            }
                        )
                    }

                }
            }

        }
    }
}


