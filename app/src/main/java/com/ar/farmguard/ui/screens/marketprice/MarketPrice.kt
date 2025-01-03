@file:OptIn(ExperimentalFoundationApi::class)

package com.ar.farmguard.ui.screens.marketprice

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ar.farmguard.domain.model.CropData
import com.ar.farmguard.ui.screens.marketprice.components.CropCard
import com.ar.farmguard.ui.shared.components.AnimatedSearchBar
import com.ar.farmguard.ui.shared.components.ContentTitle
import com.ar.farmguard.ui.shared.components.IconButton

@Composable
fun MarketPrice(){

    val state = rememberLazyListState()

    val list = getList()

    Surface {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = state,
        ) {
            item{
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }

            item{
                ContentTitle(
                    title = "Market Price",
                    icon = {
                        IconButton(
                            icon = Icons.Default.MoreVert,
                            contentDescription = "More"
                        ) { }
                    }
                )
            }

            item {
                AnimatedSearchBar(
                    onSearch = {},
                    lazyState = state  // for future
                )
            }



            items(list, key = { crop -> crop.id}){ crop ->
                Spacer(Modifier.height(8.dp))
                CropCard(
                    imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjbmEswGo92XLT45FPzdIeqTy95f-GsO1jQFCsUjsaV5V-H_Gm2rV8zaEGJ7alEFRzpTMf7hCkV67oh2q4SkTCf2emwGx4kQBzukX-PfFwZ9XC1F-tOHJD1rvqGxlXHjPbari8a-TA71eLqCgkJhKdNxHoKNgwgAzqpsjJkrsNDmZUjLjJQoORJcLgC0E4/s200/PlaygroundImage4.heic",
                    cropName = crop.name,
                    price = "₹${crop.price} / QTL",
                    marketName = crop.name,
                    isPinned = false,
                    priceColor = Color(crop.priceColor),
                    priceThread = crop.priceThread
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



fun getList(): List<CropData>{
    return listOf(
        CropData(
            id = "1",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "2",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),

        CropData(
            id = "3",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "4",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "5",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "6",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "7",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "8",
            name = "Rice",
            price = 200.0,
            image = "https://example.com/image2.jpg",
            marketName = "fatehabad",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
            id = "9",
            name = "Wheat",
            price = 100.0,
            image = "https://example.com/image1.jpg",
            marketName = "Bhattu",
            priceColor = 0xFF1E8805.toInt(),
            priceThread = "+₹20 (+13.33%)"
        ),
        CropData(
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