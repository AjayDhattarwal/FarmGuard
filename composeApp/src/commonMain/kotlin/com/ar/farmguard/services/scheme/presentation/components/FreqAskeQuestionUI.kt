package com.ar.farmguard.services.scheme.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ar.farmguard.app.utils.clickWithoutRipple
import com.ar.farmguard.services.scheme.domain.model.FaqItem

@Composable
fun freqAskQuestionUI(faqs: List<FaqItem>?) {
    var selectedIndex by remember { mutableStateOf(-1) }

    faqs?.forEachIndexed { index,item ->
        Column (
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickWithoutRipple {
                selectedIndex = if (selectedIndex == index) -1 else index
            }
        ) {
            Text(
                text = item.question,
                style = MaterialTheme.typography.titleMedium
            )
            AnimatedVisibility(
                visible = selectedIndex == index
            ){
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.answer,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }

}