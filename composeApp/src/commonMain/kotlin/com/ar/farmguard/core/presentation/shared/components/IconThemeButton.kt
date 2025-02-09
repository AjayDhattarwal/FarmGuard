package com.ar.farmguard.core.presentation.shared.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun IconThemeButton(
    icon: ImageVector,
    contentDescription : String,
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onClick: () -> Unit,
) {

    IconButton(
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


@Composable
fun IconThemeMenuButton(
    dropDownMenuOptions: List<String>,
    onMenuItemSelected: (Int) -> Unit
){
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    Box{
        IconThemeButton(
            onClick = {isDropDownMenuExpanded = !isDropDownMenuExpanded},
            icon = Icons.Default.MoreVert,
            contentDescription = "More"

        )

        DropdownMenu(
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false },
        ){
            dropDownMenuOptions.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        isDropDownMenuExpanded = false
                        onMenuItemSelected(index)
                    },
                    text = {
                        Text(
                            text = text
                        )
                    }
                )
            }
        }
    }

}


@Composable
fun IconThemeButton(
    imageVector: ImageVector,
    contentDescription: String = "",
    modifier: Modifier = Modifier,
    value: () -> Float,
    onClick: () -> Unit
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center){
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = (value() / 400f).coerceIn(0.3f, 0.9f)
                    }
                    .background(MaterialTheme.colorScheme.onBackground)
            )

            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}