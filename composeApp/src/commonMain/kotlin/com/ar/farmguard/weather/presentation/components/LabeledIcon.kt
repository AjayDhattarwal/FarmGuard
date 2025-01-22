package com.ar.farmguard.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LabeledIcon(
    icon: DrawableResource,
    label: String,
    degree: Float? = null,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = "sunRise",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
                .graphicsLayer {
                    rotationZ = degree ?: 0f
                }
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}