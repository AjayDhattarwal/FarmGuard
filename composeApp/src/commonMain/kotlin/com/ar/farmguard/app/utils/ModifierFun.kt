package com.ar.farmguard.app.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.clickWithoutRipple(onClick: () -> Unit): Modifier = this.then(
    Modifier.clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
)


@Composable
fun Modifier.brushBackground(
    isGradient: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primaryContainer.copy(0.3f)
): Modifier {

    return this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                color,
                if (!isGradient) color else MaterialTheme.colorScheme.surface
            )
        )
    )
}


@Composable
fun Modifier.loadingEffect(
    showShimmer: () -> Boolean = { true },
    targetValue: Float = 1000f,
    duration: Int = 2000,
    showBackground: Boolean = true
): Modifier {

    val shimmerColors = if(showBackground){
        listOf(
            Color.White.copy(alpha = 0.4f),
            Color(0x1462BA),
            Color(0xEA7DA2).copy(0.2f),
            Color.White.copy(alpha = 0.4f),
        )
    }else{
        listOf(
            Color.Transparent,
            Color(0x1462BA),
            Color.White.copy(0.7f),
            Color.Transparent,
        )
    }
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = ""
    )

    return drawWithCache {
        onDrawBehind {
            if (showShimmer()) {
                drawRect(
                    brush = Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(x = translateAnimation, y = translateAnimation),
                        end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
                    )
                )
            }
        }
    }

}


