package com.ar.farmguard.app.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.clickWithoutRipple(onClick: () -> Unit): Modifier = this.then(
    Modifier.clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
)


@Composable
fun Modifier.brushBackground(isGradient: Boolean = true, color: Color = MaterialTheme.colorScheme.primaryContainer.copy(0.4f)): Modifier {
    return this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                color,
                if(!isGradient) color else MaterialTheme.colorScheme.surface
            )
        )
    )
}


