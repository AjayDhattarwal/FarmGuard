package com.ar.farmguard.services.insurance.calculator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ar.farmguard.services.insurance.calculator.domain.models.calculate.CalculatedState
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*

@Composable
fun PremiumDetails(calculatedState: CalculatedState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "${calculatedState.crop} - ${calculatedState.insuranceCompanyName}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )


                PremiumDetailItem(label = "Sum Insured (Rs/Hectare)", value = calculatedState.sumInsuredPerHec)


                PremiumDetailItem(label = "Farmer Share (%)", value = calculatedState.farmerShare)


                PremiumDetailItem(label = "Actuarial Rate (%)", value = calculatedState.actuarialRate)

                PremiumDetailItem(label = "Cut Off Date", value = calculatedState.cutOfDate)

                HorizontalDivider()

                PremiumDetailItem(label = "Area", value = calculatedState.area)

                PremiumDetailItem(label = "Premium Paid by Farmer", value = calculatedState.premiumPaidByFarmer)


                PremiumDetailItem(label = "Premium Paid by Government", value = calculatedState.premiumPaidByGov)

                PremiumDetailItem(label = "Total Sum Insured", value = calculatedState.totalSumInsured)

            }
        }
    }
}



