@file:OptIn(ExperimentalFoundationApi::class)

package com.ar.farmguard.marketprice.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ar.farmguard.marketprice.domain.model.CropState
import com.ar.farmguard.marketprice.presentation.components.CropCard
import com.ar.farmguard.core.presentation.shared.components.AnimatedSearchBar
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MarketPrice(
    priceViewModel: MarketPriceViewModel = koinViewModel()
){

    val lazyColumnState = rememberLazyListState()

    val state by priceViewModel.state.collectAsState()
    val searchQuery by priceViewModel.searchQuery.collectAsState()

    val list = getList()

    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = lazyColumnState,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item{
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }

            item{
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

            item {
                AnimatedSearchBar(
                    value =  {searchQuery},
                    onValueChange = priceViewModel::updateSearchQuery,
                    lazyState = lazyColumnState  // for future
                )
            }



            items(state.commodityWithHistory, key = { crop -> crop.commodity }){ commodity ->

                Spacer(Modifier.height(8.dp))

                val todayData = commodity.tradeData.first()

                CropCard(
                    imageUrl = commodity.image,
                    cropName = commodity.commodity,
                    price = "₹${todayData.maxPrice} / QTL",
                    marketName = todayData.apmc,
                    isPinned = false,
                    priceColor = Color(commodity.priceColor),
                    priceThread = commodity.currentPriceThread
                )

            }
        }
    }
}

@Preview
@Composable
fun MarketPricePreview(){
    MarketPrice()
}



fun getList(): List<CropState>{
    return listOf(
        CropState(
            id = "1",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "2",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),

        CropState(
            id = "3",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "4",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "5",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "6",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "7",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "8",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "9",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropState(
            id = "10",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),

    )
}