package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NoteText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    color:  Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(0.7f)
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Text(
            text = text,
            style = style,
            color = color,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Preview
@Composable
fun NoteTextPrev() {
    NoteText(
        text = "Sunny weather is expected over the weekend. A great time for outdoor harvesting activities."
    )
}