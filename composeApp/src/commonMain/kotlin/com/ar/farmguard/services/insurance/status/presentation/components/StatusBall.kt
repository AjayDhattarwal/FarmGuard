package com.ar.farmguard.services.insurance.status.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun StatusBall(status: String) {
    val color = when (status.lowercase()) {
        "done" -> Color.Green
        "cancelled" -> Color.Red
        else -> Color.Gray
    }

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(48.dp)) {
            drawCircle(color = color, radius = size.minDimension / 2, )
        }
        Icon(
            imageVector = if (status.lowercase() == "done") Icons.Default.Done else Icons.Default.Close,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}


