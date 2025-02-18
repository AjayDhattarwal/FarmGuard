package com.ar.farmguard.services.insurance.status.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.shared.components.VerticalGridLayout
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusData

@Composable
fun ApplicationStatusCard(applicationStatusData: ApplicationStatusData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {

            VerticalGridLayout(
                columns = 2,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                item{
                    ApplicationDetailItem(
                        label = "Farmer Name",
                        value = applicationStatusData.farmerName
                    )
                }

                item{
                    ApplicationDetailItem(
                        label = "Village Name",
                        value = applicationStatusData.villageName
                    )
                }

                item{
                    ApplicationDetailItem(label = "Scheme", value = applicationStatusData.scheme)
                }

                item{
                    ApplicationDetailItem(label = "Season", value = applicationStatusData.seasons)
                }

                item{
                    ApplicationDetailItem(
                        label = "Crop Name",
                        value = applicationStatusData.cropName
                    )
                }

                item{
                    ApplicationDetailItem(label = "Year", value = applicationStatusData.year)
                }

            }


            Spacer(modifier = Modifier.height(8.dp))

            ApplicationDetailItem(label = "Policy ID", value = applicationStatusData.policyId)

            ApplicationDetailItem(
                label = "Application No",
                value = applicationStatusData.applicationNo
            )


            Spacer(modifier = Modifier.height(8.dp))

            ThreeStepStatusTimeline(
                currentStatus = applicationStatusData.policyStatus.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = applicationStatusData.message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }

    }

}

