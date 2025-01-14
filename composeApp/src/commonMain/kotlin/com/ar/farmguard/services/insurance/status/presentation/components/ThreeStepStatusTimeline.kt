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
    applicationStatus: String,
    paymentStatus: String,
    finalStatus: String
) {
    val steps = listOf(
        "Application\nSubmission" to applicationStatus,
        "Payment" to paymentStatus,
        "Status" to finalStatus
    )

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        steps.forEachIndexed { index, (label, status) ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().weight(if(index != 0) 1f else 0.49f)
            ){
                if (index != 0) {
                    ConnectorLine(
                        modifier = Modifier.weight(1f).offset {
                            IntOffset(x= 0 , y = -50)
                        },
                        status = status
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    StatusBall(
                        status = status
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