package com.ar.farmguard.core.presentation.shared.components


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun IconThemeButton(
    icon: ImageVector,
    contentDescription : String,
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onClick: () -> Unit,
) {

    androidx.compose.material3.IconButton(
        onClick = onClick,
        colors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}