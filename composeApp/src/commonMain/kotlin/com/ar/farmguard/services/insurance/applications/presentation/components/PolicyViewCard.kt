package com.ar.farmguard.services.insurance.applications.presentation.components

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
import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyData
import com.ar.farmguard.services.insurance.status.presentation.components.ApplicationDetailItem
import com.ar.farmguard.services.insurance.status.presentation.components.ThreeStepStatusTimeline

@Composable
fun PolicyViewCard(policyData: UserPolicyData) {
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
                    policyData.villageName?.let {
                        ApplicationDetailItem(
                            label = "Village Name",
                            value = it
                        )
                    }
                }

                item{
                    policyData.policyArea.let {
                        ApplicationDetailItem(
                            label = "Area (Hect.)",
                            value = it.toString()
                        )
                    }
                }

                item{
                    policyData.cropName?.let {
                        ApplicationDetailItem(
                            label = "Crop Name",
                            value = it
                        )
                    }
                }

                item{
                    policyData.sumInsured?.let{
                        ApplicationDetailItem(
                            label = "Sum Insured (Rs).",
                            value = it.toString()
                        )
                    }
                }

                item{
                    policyData.farmerShare?.let{
                        ApplicationDetailItem(
                            label = "Farmer Premium (Rs).",
                            value = it.toString()
                        )
                    }
                }

                item{
                    policyData.goiShare?.let {
                        ApplicationDetailItem(
                            label = "Govt. Subsidy (Rs).",
                            value = it.toString()
                        )
                    }
                }

                item{
                    policyData.policyStatus?.let {
                        ApplicationDetailItem(
                            label = "Policy Status",
                            value = it
                        )
                    }
                }

                item{
                    policyData.claimType?.let {
                        ApplicationDetailItem(
                            label = "Claim Type.",
                            value = it
                        )
                    }
                }

                item{
                    policyData.claimAmount?.let {
                        ApplicationDetailItem(
                            label = "Claim Amount.",
                            value = it.toString()
                        )
                    }

                }

                item{
                    policyData.claimDate?.let {
                        ApplicationDetailItem(
                            label = "Claim Date.",
                            value = it
                        )
                    }
                }

                item{
                    policyData.claimStatus?.let {
                        ApplicationDetailItem(
                            label = "Claim Status.",
                            value = it
                        )
                    }
                }

                item{
                    policyData.accountNumber?.let {
                        ApplicationDetailItem(
                            label = "Account No.",
                            value = it
                        )
                    }
                }

                item{
                    policyData.utrNumber?.let { ApplicationDetailItem(label = "UTR No.", value = it) }
                }



            }


            Spacer(modifier = Modifier.height(8.dp))

            ApplicationDetailItem(label = "Application No.", value = policyData.applicationNo)


            Spacer(modifier = Modifier.height(8.dp))

            ThreeStepStatusTimeline(
                currentStatus = policyData.policyStatus
            )

            policyData.insuranceCompanyName?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }
        }

    }


}