package com.ar.farmguard.ui.shared.components


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun IconButton(
    icon: ImageVector,
    contentDescription : String,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
) {

    androidx.compose.material3.IconButton(
        onClick = onClick,
        colors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}