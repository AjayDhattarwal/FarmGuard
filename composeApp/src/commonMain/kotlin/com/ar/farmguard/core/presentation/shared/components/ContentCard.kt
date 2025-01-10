package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ar.farmguard.app.utils.brushBackground
import com.ar.farmguard.app.utils.clickWithoutRipple

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isGradient: Boolean = true,
    content: @Composable () -> Unit
){
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .clickWithoutRipple(onClick = onClick),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(
            modifier = Modifier
                .brushBackground(isGradient)

        ) {
            content()
        }
    }
}