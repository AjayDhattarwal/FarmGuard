package com.ar.farmguard.marketprice.presentation.commodity_details.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ar.farmguard.marketprice.domain.model.state.TradeReport
import kotlinx.coroutines.launch

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
        val background = MaterialTheme.colorScheme.background

        val animationProgress = remember(graphType) {
            Animatable(0f)
        }

        var coordinate by remember {
            mutableStateOf<Offset?>(null)
        }

        var selectedValue by remember { mutableStateOf< Pair<Int, Int>?>(null) }

        val animatedYOffset = remember { Animatable(1f) }

        var offsetList by remember { mutableStateOf<List<Offset>>( emptyList()) }


        LaunchedEffect(key1 = graphType) {
            coordinate = null
            selectedValue = null
            animationProgress.animateTo(1f, tween(3000))
        }

        val scope = rememberCoroutineScope()

        val textMeasurer = rememberTextMeasurer()


        Column {

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

                                val pair =
                                    generatePath(xAxisCoordinates, graphCoordinates, size)

                                offsetList = pair.second

                                val path = pair.first


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
                        .pointerInput(Unit){
                            detectTapGestures(
                                onTap = { tapOffset ->
                                    val closeIconSize = 12.dp.toPx()
                                    val padding = 9.dp.toPx()
                                    val closeIconBounds = Rect(
                                        left = size.width - closeIconSize - padding,
                                        top = padding,
                                        right = size.width - padding,
                                        bottom = closeIconSize + padding
                                    )

                                    if (closeIconBounds.contains(tapOffset)) {
                                        scope.launch {
                                            animatedYOffset.animateTo(1f)
                                        }

                                    } else {
                                        val x = (size.width / xAxisCoordinates.size) - 2.dp.toPx()
                                        val y = size.height / yAxisValues.size

                                        val xIndex = ((tapOffset.x / x).toInt()).coerceIn(0, xAxisCoordinates.lastIndex)
                                        val yIndex = ((tapOffset.y / y).toInt()).coerceIn(0, yAxisValues.lastIndex)

                                        val offset = offsetList.get(xIndex)

                                        selectedValue = Pair(xIndex, yIndex)
                                        coordinate = offset

                                        scope.launch {
                                            animatedYOffset.snapTo(1f)
                                            animatedYOffset.animateTo(
                                                targetValue = 0f,
                                                animationSpec = tween(durationMillis = 1000)
                                            )
                                        }
                                    }
                                }
                            )
                        }
                )

                Spacer(
                    modifier = Modifier.padding(26.dp)
                        .aspectRatio(3 / 2f)
                        .fillMaxSize()
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


                Spacer(
                    modifier = Modifier
                        .padding(26.dp)
                        .aspectRatio(3 / 2f)
                        .fillMaxSize()
                        .drawWithCache {
                            onDrawBehind {
                                val screenWidth = size.width
                                val screenHeight = size.height

                                val triangleHeight = 20.dp.toPx()
                                val triangleWidth = 30.dp.toPx()
                                val cardWidth = 200.dp.toPx()
                                val cardHeight = 100.dp.toPx()
                                val padding = 9.dp.toPx()


                                coordinate?.let { coordinate ->
                                    val data = listOfTrade.reversed()[selectedValue!!.first]

                                    val pointerX = coordinate.x
                                    val pointerY = coordinate.y + animatedYOffset.value

                                    val closeIconSize = 12.dp.toPx()

                                    val closeIconTopLeft = Offset(
                                        x = size.width - closeIconSize - padding,
                                        y = padding
                                    )


                                    val adjustedX = (pointerX - cardWidth / 2).coerceIn(
                                        padding,
                                        screenWidth - cardWidth - padding
                                    )

                                    val pointerDirection = when {
                                        pointerY < screenHeight / 2 -> "down"
                                        pointerY > screenHeight / 2 -> "up"
                                        pointerX < screenWidth / 2 -> "right"
                                        else -> "left"
                                    }

                                    var yTop = pointerY
                                    var path = Path()

                                    when (pointerDirection) {
                                        "down" -> {
                                            yTop = (pointerY + triangleHeight).coerceIn(padding, screenHeight - cardHeight - padding)
                                            path = Path().apply {
                                                moveTo(pointerX, pointerY)
                                                lineTo(pointerX - triangleWidth / 2, pointerY + triangleHeight)
                                                lineTo(pointerX + triangleWidth / 2, pointerY + triangleHeight)
                                                close()
                                            }
                                        }
                                        "up" -> {
                                            yTop = (pointerY - cardHeight - triangleHeight).coerceIn(padding, screenHeight - cardHeight - padding)
                                            path = Path().apply {
                                                moveTo(pointerX, pointerY)
                                                lineTo(pointerX - triangleWidth / 2, pointerY - triangleHeight)
                                                lineTo(pointerX + triangleWidth / 2, pointerY - triangleHeight)
                                                close()
                                            }
                                        }
                                        "right" -> {
                                            yTop = (pointerY - cardHeight / 2).coerceIn(padding, screenHeight - cardHeight - padding)
                                            path = Path().apply {
                                                moveTo(pointerX, pointerY)
                                                lineTo(pointerX + triangleHeight, pointerY - triangleWidth / 2)
                                                lineTo(pointerX + triangleHeight, pointerY + triangleWidth / 2)
                                                close()
                                            }
                                        }
                                        "left" -> {
                                            yTop = (pointerY - cardHeight / 2).coerceIn(padding, screenHeight - cardHeight - padding)
                                            path = Path().apply {
                                                moveTo(pointerX, pointerY)
                                                lineTo(pointerX - triangleHeight, pointerY - triangleWidth / 2)
                                                lineTo(pointerX - triangleHeight, pointerY + triangleWidth / 2)
                                                close()
                                            }
                                        }
                                    }

                                    val lineThickness = 2.dp.toPx()
                                    val halfSize = closeIconSize / 2

                                    val closeIconCenter = Offset(
                                        x = size.width - closeIconSize / 2 - padding,
                                        y = closeIconSize / 2 + padding
                                    )


                                    clipRect(top = size.height * animatedYOffset.value) {

                                        drawLine(
                                            color = Color.Red,
                                            start = closeIconCenter - Offset(halfSize, halfSize),
                                            end = closeIconCenter + Offset(halfSize, halfSize),
                                            strokeWidth = lineThickness
                                        )
                                        drawLine(
                                            color = Color.Red,
                                            start = closeIconCenter - Offset(-halfSize, halfSize),
                                            end = closeIconCenter + Offset(-halfSize, halfSize),
                                            strokeWidth = lineThickness
                                        )

                                        drawPath(
                                            path = path,
                                            color = barColor.copy(0.9f),
                                        )

                                        drawRoundRect(
                                            color = barColor.copy(0.9f),
                                            topLeft = Offset(adjustedX, yTop ),
                                            size = Size(cardWidth + 10, cardHeight + 20),
                                            cornerRadius = CornerRadius(8.dp.toPx()),
                                            blendMode = BlendMode.Src
                                        )

                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = "Arrival:       ${data.commodityArrivals} /QTL",
                                            topLeft = Offset(adjustedX + padding, yTop + padding ),
                                            style = TextStyle(
                                                color = background,
                                                fontSize = 12.sp
                                            )

                                        )
                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = "Traded:      ${data.commodityTraded} / QTL",
                                            topLeft = Offset(
                                                adjustedX + padding,
                                                yTop + padding * 3
                                            ),
                                            style = TextStyle(
                                                color = background,
                                                fontSize = 12.sp
                                            )
                                        )
                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = "Min Price:  ${data.minPrice}",
                                            topLeft = Offset(
                                                adjustedX + padding,
                                                yTop + padding * 5
                                            ),
                                            style = TextStyle(
                                                color = background,
                                                fontSize = 12.sp
                                            )
                                        )
                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = "Avg Price:  ${data.modalPrice}",
                                            topLeft = Offset(
                                                adjustedX + padding,
                                                yTop + padding * 7
                                            ),
                                            style = TextStyle(
                                                color = background,
                                                fontSize = 12.sp
                                            )
                                        )
                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = "Max Price: ${data.maxPrice}",
                                            topLeft = Offset(
                                                adjustedX + padding,
                                                yTop + padding * 9
                                            ),
                                            style = TextStyle(
                                                color = background,
                                                fontSize = 12.sp
                                            )
                                        )
                                    }
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
): Pair<Path, List<Offset>> {

    val path = Path()
    val points = mutableListOf<Offset>()
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
        val balanceX = i * weekWidth
        val balanceY = (size.height - adjustedY * heightPxPerAmount)

        points.add(Offset(balanceX, balanceY))

        if (i == 0) {
            path.moveTo(balanceX, balanceY)
        } else {
            val prevBalance = data[i - 1]
            val prevX = (i - 1) * weekWidth
            val prevY = (size.height - (prevBalance.amount - min.amount) * heightPxPerAmount)

            val controlX1 = prevX + weekWidth / 2
            val controlY1 = prevY.toFloat()
            val controlX2 = balanceX - weekWidth / 2
            path.cubicTo(controlX1, controlY1, controlX2, balanceY, balanceX, balanceY)
        }
    }

    return path to points
}


data class GraphData(val date: Int, val amount: Double)



fun getYaxisValue(min: Double, max: Double, size: Int): List<Long> {
    require(size > 0) { "Size must be greater than 0" }
    require(min <= max) { "Minimum value must be less than or equal to maximum value" }

    val step = (max - min) / (size - 1)
    return List(size) { index -> (min + index * step).toLong() }.reversed()
}



