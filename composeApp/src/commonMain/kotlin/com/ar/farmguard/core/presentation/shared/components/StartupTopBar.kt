package com.ar.farmguard.core.presentation.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartupTopBar(title: String = "", buttonTitle: String,onLoginClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            TextButton(onClick = onLoginClick) {
                Text(text = buttonTitle)
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}
