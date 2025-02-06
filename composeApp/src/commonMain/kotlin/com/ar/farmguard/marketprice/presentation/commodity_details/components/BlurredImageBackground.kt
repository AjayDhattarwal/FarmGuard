@file:OptIn(ExperimentalMaterial3Api::class)

package com.ar.farmguard.marketprice.presentation.commodity_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.ar.farmguard.core.presentation.shared.components.IconThemeButton

@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    onBackClick: () -> Unit,
    isWithSubImage: Boolean = true,
    modifier: Modifier = Modifier,
    titleContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val backgroundColor = MaterialTheme.colorScheme.background

    val scrollState = rememberScrollState()



    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if(size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconThemeButton(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "goBack",
                        value = {scrollState.value.toFloat()},
                        onClick = onBackClick,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                )
            )
        }
    ) {
        Box(modifier = modifier) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "crop cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                alpha =   1 -  (scrollState.value.toFloat() / 400f)
                            }
                            .blur(10.dp)
                    )

                    Box(
                        modifier.fillMaxSize()
                            .drawBehind {
                                drawRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            if(isWithSubImage) backgroundColor.copy(0.3f) else Color.Transparent,
                                            backgroundColor
                                        ),
                                        startY = 0f,
                                        endY = size.height
                                    )
                                )

                            }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.TopCenter)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {

                if(isWithSubImage){
                    Spacer(modifier = Modifier.height(100.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .height(200.dp)
                                .aspectRatio(2f / 3f),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Image(
                                painter = painter,
                                contentDescription = "crop cover",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Transparent),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            titleContent()
                        }
                    }
                }else{
                    Spacer(modifier = Modifier.height(130.dp))
                }

                content()

                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}