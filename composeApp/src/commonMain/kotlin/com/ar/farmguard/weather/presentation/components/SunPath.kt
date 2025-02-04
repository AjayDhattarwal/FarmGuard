package com.ar.farmguard.weather.presentation.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp.Companion.Difference
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.parseTime
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt


fun DrawScope.drawSunPath(
    riseTime: String,
    setTime: String,
    currentTime: String,
    isSun: Boolean,
    animateProgress: () -> Float
) {

    val color = if(isSun) Color(0xFFFFD700) else Color(0xFFAEAEF8)
    val pathColor = if(isSun) Color(0xFFDFB860) else Color(0xFFAEAEE0)

    val sunriseMinutes = parseTime(riseTime, isSun) ?: return
    val sunsetMinutes = parseTime(setTime, isSun) ?: return
    val currentMinutes = parseTime(currentTime, isSun) ?: return


    val progress = ((currentMinutes - sunriseMinutes).inWholeMinutes.toFloat() /
            (sunsetMinutes - sunriseMinutes).inWholeMinutes.toFloat())
        .coerceIn(0f, 1f)

    val p0 = Offset(0f, size.height )
    val p1 = Offset(size.width / 2f, size.height / 2f - size.width / 4f)
    val p2 = Offset(size.width, size.height)


    val path = Path().apply {
        moveTo(p0.x, p0.y)
        quadraticTo(p1.x, p1.y, p2.x, p2.y)
    }
    drawPath(
        path = path,
        color = pathColor,
        style = Stroke(width = 2.dp.toPx()),
    )

    val sunPosition = positionOnPathByProgress(progress * animateProgress(), p0, p1, p2)


    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                color.copy(alpha = 0.5f),
                color.copy(alpha = 0.1f),
                Color.Transparent
            ),
            center = sunPosition,
            radius = 40.dp.toPx()
        ),
        radius = 40.dp.toPx(),
        center = sunPosition
    )


    if(isSun) {
        drawSun(
            color = color,
            center = sunPosition
        )

    } else{
        drawMoon(
            color = color,
            center = sunPosition
        )
    }
}


fun calculatePathLength(p0: Offset, p1: Offset, p2: Offset, steps: Int = 100): Float {
    var length = 0f
    var prevPoint = p0

    for (i in 1..steps) {
        val t = i / steps.toFloat()
        val currentPoint = bezierPoint(t, p0, p1, p2)
        length += distanceBetween(prevPoint, currentPoint)
        prevPoint = currentPoint
    }

    return length
}

/**
 * Finds a position on a quadratic Bézier curve based on progress [t].
 */
fun bezierPoint(t: Float, p0: Offset, p1: Offset, p2: Offset): Offset {
    val u = 1 - t
    return Offset(
        x = u.pow(2) * p0.x + 2 * u * t * p1.x + t.pow(2) * p2.x,
        y = u.pow(2) * p0.y + 2 * u * t * p1.y + t.pow(2) * p2.y
    )
}

/**
 * Calculates the position on a Bézier curve based on progress.
 * This uses numerical methods to match the progress to the curve length.
 */
fun positionOnPathByProgress(
    progress: Float,
    p0: Offset,
    p1: Offset,
    p2: Offset,
    steps: Int = 100
): Offset {
    val totalLength = calculatePathLength(p0, p1, p2, steps)
    val targetLength = totalLength * progress

    var accumulatedLength = 0f
    var prevPoint = p0

    for (i in 1..steps) {
        val t = i / steps.toFloat()
        val currentPoint = bezierPoint(t, p0, p1, p2)
        val segmentLength = distanceBetween(prevPoint, currentPoint)

        if (accumulatedLength + segmentLength >= targetLength) {
            val remaining = targetLength - accumulatedLength
            val ratio = remaining / segmentLength
            return interpolateBetween(prevPoint, currentPoint, ratio)
        }

        accumulatedLength += segmentLength
        prevPoint = currentPoint
    }

    return p2 // Fallback: return the end point if progress exceeds bounds
}

/**
 * Computes the distance between two points.
 */
fun distanceBetween(p1: Offset, p2: Offset): Float {
    return sqrt((p2.x - p1.x).pow(2) + (p2.y - p1.y).pow(2))
}

/**
 * Interpolates between two points based on [ratio].
 */
fun interpolateBetween(p1: Offset, p2: Offset, ratio: Float): Offset {
    return Offset(
        x = p1.x + (p2.x - p1.x) * ratio,
        y = p1.y + (p2.y - p1.y) * ratio
    )
}




private fun DrawScope.drawMoon(
    color: Color,
    center: Offset,
    radius: Float = 8.dp.toPx()
) {
    val outerCirclePath = Path().apply {
        addOval(Rect(center = center, radius = radius))
    }

    val innerCirclePath = Path().apply {
        addOval(Rect(center = center.copy(x = center.x - radius * 0.6f, y = center.y - radius * 0.5f), radius = radius))
    }

    clipPath(
        path = innerCirclePath,
        clipOp = Difference
    ) {
        drawPath(
            path = outerCirclePath,
            color = color,
            style = Fill
        )
    }


}


private fun DrawScope.drawSun(
    color: Color = Color(0xFFFFD700),
    center: Offset
){
    drawCircle(
        color = color,
        radius = 7.dp.toPx(),
        center = center
    )

    val rayLength = 5.dp.toPx()
    val rayWidth = 1.dp.toPx()
    for (angle in 0 until 360 step 24) {
        val radians = angle.toDouble() * (PI / 180.0)
        val startX = center.x + 5.dp.toPx() * kotlin.math.cos(radians).toFloat()
        val startY = center.y + 5.dp.toPx() * kotlin.math.sin(radians).toFloat()
        val endX =
            center.x + (5.dp.toPx() + rayLength) * kotlin.math.cos(radians).toFloat()
        val endY =
            center.y + (5.dp.toPx() + rayLength) * kotlin.math.sin(radians).toFloat()

        drawLine(
            color = color.copy(0.85f),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = rayWidth,
            cap = StrokeCap.Round
        )
    }
}