package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimeChip(modifier: Modifier = Modifier, timestamp: String) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                shape = MaterialTheme.shapes.small
            )
    ) {
        Text(
            text = timestamp,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}