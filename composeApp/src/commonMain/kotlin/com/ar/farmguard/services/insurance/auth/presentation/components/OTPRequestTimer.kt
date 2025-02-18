package com.ar.farmguard.services.insurance.auth.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun OTPRequestTimer(onTimerFinish: () -> Unit) {
    var timeLeft by remember { mutableStateOf(60) }

    LaunchedEffect(timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        }
        onTimerFinish()
    }

    TextButton(onClick = {}){
        Text(text = "0:${timeLeft}s", style = MaterialTheme.typography.labelSmall)
    }
}
