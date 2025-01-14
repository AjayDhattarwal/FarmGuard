@file:OptIn(ExperimentalMaterial3Api::class)

package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackPress: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
){
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackPress){
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "BackPress"
                )
            }
        },
        actions = actions
    )
}

@Composable
fun TopBarWithMenu(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackPress: () -> Unit,
    dropDownMenuOptions: List<String>,
    onMenuItemSelected: (Int) -> Unit
){
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }


    TopBar(
        modifier = modifier,
        title = title,
        onBackPress = {
            isDropDownMenuExpanded = false
            onBackPress()
        }
    ){
        IconButton(onClick = {isDropDownMenuExpanded = !isDropDownMenuExpanded}) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "BackPress"
            )
        }

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
                    },

                )
            }
        }
    }

}
