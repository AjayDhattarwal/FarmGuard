package com.ar.farmguard.marketprice.presentation.commodity_details.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ar.farmguard.marketprice.domain.model.state.TradeReport

enum class GraphTypes{
    MAX, AVG, MIN
}

@Composable
fun CropTrendGraph(
    listOfTrade: List<TradeReport>,
    graphType: GraphTypes = GraphTypes.MAX,
) {

    val dataList = when(graphType){
        GraphTypes.MAX -> listOfTrade.map { it.maxPrice }
        GraphTypes.AVG -> listOfTrade.map { it.modalPrice }
        GraphTypes.MIN -> listOfTrade.map { it.minPrice }
    }

    val graphColor = when(graphType){
        GraphTypes.MAX -> Color.Green
        GraphTypes.AVG -> Color.Blue
        GraphTypes.MIN -> Color.Red
    }


    val minPrice = dataList.minOf { it }
    val maxPrice = dataList.maxOf { it }
    val graphCoordinates = dataList.map { it }

    val yListSize = dataList.size - 1

    if(yListSize > 0){
        val yAxisValues = getYaxisValue(min = minPrice, max = maxPrice, size = yListSize)

        val xAxisCoordinates = listOfTrade.map {
            it.createdAt.substringAfterLast("-").take(2).toInt()
        }.reversed()

        val barColor = MaterialTheme.colorScheme.onBackground

        val animationProgress = remember(graphType) {
            Animatable(0f)
        }
        LaunchedEffect(key1 = graphType, block = {

            animationProgress.animateTo(1f, tween(3000))
        })
        val textMeasurer = rememberTextMeasurer()

        Column{

            Box{
                Spacer(
                    modifier = Modifier.padding(26.dp)
                        .aspectRatio(3 / 2f)
                        .fillMaxSize()
                        .drawWithCache {
                            onDrawBehind {

                                val yAxisSpace = size.height / (yAxisValues.size)
                                val xAxisSpace = (size.width / xAxisCoordinates.size)

                                val horizontalLines = yAxisValues.size

                                val barWidthPx = 1.dp.toPx()

                                repeat(horizontalLines) { i ->
                                    val startY = yAxisSpace * (i + 1)

                                    drawLine(
                                        barColor.copy(0.3f),
                                        start = Offset(0f, startY),
                                        end = Offset(size.width, startY),
                                        strokeWidth = barWidthPx
                                    )
                                }

                                repeat(xAxisCoordinates.size) {
                                    val x = it * xAxisSpace
                                    drawLine(
                                        barColor.copy(0.3f),
                                        start = Offset(x, 0f),
                                        end = Offset(x, size.height),
                                        strokeWidth = barWidthPx
                                    )

                                }


                            }
                        }
                )

                Spacer(
                    modifier = Modifier
                        .padding(26.dp)
                        .aspectRatio(3 / 2f)
                        .fillMaxSize()
                        .drawWithCache {
                            onDrawBehind {

                                drawRect(
                                    color = barColor,
                                    style = Stroke(2.dp.toPx())
                                )

                                val path =
                                    generatePath(xAxisCoordinates, graphCoordinates, size)

                                val filledPath = Path()
                                filledPath.addPath(path)
                                filledPath.lineTo(size.width, size.height)
                                filledPath.lineTo(0f, size.height)
                                filledPath.close()

                                val maxBrush = Brush.verticalGradient(
                                    listOf(
                                        graphColor.copy(0.4f),
                                        Color.Transparent
                                    )

                                )

                                clipRect(right = size.width * animationProgress.value) {
                                    drawPath(
                                        path = path,
                                        color = graphColor,
                                        style = Stroke(2.dp.toPx())
                                    )
                                    drawPath(
                                        path = filledPath,
                                        brush = maxBrush,
                                        style = Fill
                                    )
                                }
                            }
                        }
                )

                Spacer(
                    modifier = Modifier.padding(26.dp)
                        .aspectRatio(3 / 2f)
                        .drawWithCache {
                            onDrawBehind {
                                val yAxisSpace = size.height / (yAxisValues.size)
                                val lastIndex = yAxisValues.lastIndex

                                yAxisValues.forEachIndexed { i, value ->
                                    drawText(
                                        textMeasurer = textMeasurer,
                                        text = value.toString(),
                                        topLeft = Offset(
                                            -26.dp.toPx(),
                                            (yAxisSpace * (i + 1)) - if (i == lastIndex) 9.dp.toPx() else 4.dp.toPx()
                                        ),
                                        style = TextStyle(
                                            color = barColor,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }
                )
            }
            Spacer(
                Modifier
                    .padding(horizontal = 26.dp).fillMaxWidth()
                    .drawWithCache {
                        onDrawBehind {
                            xAxisCoordinates.forEachIndexed { i, value ->
                                val xAxisSpace = (size.width / xAxisCoordinates.size)
                                drawText(
                                    textMeasurer = textMeasurer,
                                    text = value.toString(),
                                    topLeft = Offset((xAxisSpace * i) - 10, size.height - 14.dp.toPx()),
                                    style = TextStyle(color = barColor, fontSize = 10.sp)
                                )
                            }
                        }
                    }
            )

        }
    } else{
        Box(
            modifier = Modifier.padding(26.dp)
                .aspectRatio(3 / 2f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Graph Not Available",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.graphicsLayer {
                    rotationZ = -45f
                }
            )
        }
    }

}

fun generatePath(
    xAxisData: List<Int>,
    yAxisData: List<Double>,
    size: Size,
): Path {

    val path = Path()
    val data = xAxisData.reversed().zip(yAxisData.reversed()) { x, y ->
        GraphData(x, y)
    }

    val numberEntries = data.size

    val weekWidth = size.width / numberEntries.coerceAtLeast(1)
    val priceHeight = ((size.height + weekWidth) / numberEntries)

    val max = data.maxBy { it.amount }
    val min = data.minBy { it.amount }

    val range = max.amount - min.amount
    val heightPxPerAmount = ((size.height - priceHeight) / range.toFloat())

    data.forEachIndexed { i, balance ->
        val adjustedY = (balance.amount - min.amount).toFloat()
        if (i == 0) {
            path.moveTo(
                0f,
                (size.height - adjustedY  * heightPxPerAmount)
            )
        } else {
            val prevBalance = data[i - 1]
            val prevX = (i - 1) * weekWidth
            val prevY = (size.height - (prevBalance.amount - min.amount ) * heightPxPerAmount)
            val balanceX = i * weekWidth
            val balanceY = (size.height - adjustedY * heightPxPerAmount)


            val controlX1 = prevX + weekWidth / 2
            val controlY1 = prevY.toFloat()
            val controlX2 = balanceX - weekWidth / 2
            path.cubicTo(controlX1, controlY1, controlX2, balanceY, balanceX, balanceY)
        }
    }
    return path
}

data class GraphData(val date: Int, val amount: Double)



fun getYaxisValue(min: Double, max: Double, size: Int): List<Long> {
    require(size > 0) { "Size must be greater than 0" }
    require(min <= max) { "Minimum value must be less than or equal to maximum value" }

    val step = (max - min) / (size - 1)
    return List(size) { index -> (min + index * step).toLong() }.reversed()
}



