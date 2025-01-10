package com.ar.farmguard.app

import androidx.compose.runtime.*
import com.ar.farmguard.app.presentation.navigation.NavigationGraph
import com.ar.farmguard.app.presentation.theme.FarmGuardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    FarmGuardTheme {
        NavigationGraph()
    }
}