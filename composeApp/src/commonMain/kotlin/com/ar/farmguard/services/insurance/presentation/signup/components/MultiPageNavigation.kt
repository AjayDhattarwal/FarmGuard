package com.ar.farmguard.services.insurance.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultiPageNavigation(
    currentPage: Int = 0,
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        // Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentPage > 1) {
                Button(onClick = onPrevious) {
                    Text("Previous")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (currentPage < 4) {
                Button(onClick = onNext) {
                    Text("Next")
                }
            } else {
                Button(onClick = onSubmit) {
                    Text("Submit")
                }
            }
        }
    }
}