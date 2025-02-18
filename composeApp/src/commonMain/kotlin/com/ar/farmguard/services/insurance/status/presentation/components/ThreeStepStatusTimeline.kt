package com.ar.farmguard.services.insurance.status.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun ThreeStepStatusTimeline(
    currentStatus: String?
) {

    val statusIndex = when(currentStatus){
        "APPROVED" -> 2
        "PAID" -> 1
        "UNPAID" -> 0
        else -> - 1
    }


    val steps = listOf(
        "Application\nSubmission",
        "Payment",
        "Status"
    )


    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        steps.forEachIndexed { index, label ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().weight(if(index != 0) 1f else 0.49f)
            ){
                if (index != 0) {
                    ConnectorLine(
                        modifier = Modifier.weight(1f).offset {
                            IntOffset(x= 0 , y = -50)
                        },
                        status = if(statusIndex >= index) "done"  else if(statusIndex == -1 ) "cancelled" else "pending"

                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    StatusBall(
                        status = if(statusIndex >= index) "done"  else if(statusIndex == -1 ) "cancelled" else "pending"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        minLines = 2
                    )
                }
            }
        }
    }
}