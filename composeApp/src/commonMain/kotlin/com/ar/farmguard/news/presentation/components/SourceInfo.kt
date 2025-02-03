package com.ar.farmguard.news.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SourceInfo(source: String, author: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.AccountBox,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(0.8f),
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = source,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.8f)
        )

        author?.let {
            Text(
                text = " • $it",
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.8f)
            )
        }
    }
}