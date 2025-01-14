package com.ar.farmguard.services.insurance.status.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun ConnectorLine(
    modifier: Modifier = Modifier,
    status: String
) {
    val color = when (status.lowercase()) {
        "done" -> Color.Green
        "cancelled" -> Color.Red
        else -> Color.Gray
    }

    Canvas(modifier = modifier.height(2.dp)) {
        drawLine(
            color = color,
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2)
        )
    }
}