package com.ar.farmguard.ui.shared.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ContentTitle(
    title: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    style: TextStyle = MaterialTheme.typography.titleMedium,
    titleColor: Color = MaterialTheme.colorScheme.primary,
    space: Dp = 8.dp,
    content: @Composable () -> Unit
){
    Column(modifier) {
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = title,
                style = style,
                color = titleColor
            )
            Spacer(Modifier.weight(1f))
            icon()
        }
        Spacer(Modifier.height(space))
        content()
    }
}