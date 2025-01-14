@file:OptIn(ExperimentalMaterial3Api::class)

package com.ar.farmguard.services.insurance.calculator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ar.farmguard.core.presentation.navigation.SubGraph
import com.ar.farmguard.core.presentation.shared.components.TopBarWithMenu
import com.ar.farmguard.services.insurance.auth.signup.components.LabeledTextField
import com.ar.farmguard.services.insurance.auth.signup.components.SelectorField
import com.ar.farmguard.services.insurance.calculator.presentation.components.PremiumDetails
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun PremiumCalculator(
    calculatorViewModel: PremiumCalculatorViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit
){

    val message by calculatorViewModel.message.collectAsState()

    val state by calculatorViewModel.state.collectAsState()

    val sssyData by calculatorViewModel.sssyData.collectAsState()

    val premiumCalculatorState by calculatorViewModel.calculatedPremium.collectAsState()

    var showDetails by remember{ mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopBarWithMenu(
                title = "Premium Calculator",
                dropDownMenuOptions = listOf("Settings"),
                onBackPress = onBackPress,
                onMenuItemSelected = {
                    when(it){
                        0 -> navigate(SubGraph.Settings)
                    }
                }
            )
        },
        modifier = Modifier.imePadding()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            message?.let {
                message?.color?.let { color ->
                    Text(
                        text = it.uiText?.asString().toString(),
                        color = color,
                        maxLines = 2
                    )
                }
            }

            SelectorField(
                options = sssyData.seasons,
                selectedOption = state.season,
                onOptionSelected = {
                    calculatorViewModel.saveField("season", it)
                },
                placeholder = "Season *"
            )

            SelectorField(
                options = sssyData.years,
                selectedOption = state.year,
                onOptionSelected = {
                    calculatorViewModel.saveField("year", it)
                },
                readOnly = state.season.isBlank(),
                placeholder = "Year *"
            )

            SelectorField(
                options = sssyData.schemes,
                selectedOption = state.scheme,
                onOptionSelected = {
                    calculatorViewModel.saveField("scheme", it)
                },
                readOnly = state.year.isBlank(),
                placeholder = "Scheme *"
            )

            SelectorField(
                options = sssyData.states,
                selectedOption = state.state,
                onOptionSelected = {
                    calculatorViewModel.saveField("state", it)
                },
                readOnly = state.scheme.isBlank(),
                placeholder = "State *"
            )

            SelectorField(
                options = sssyData.districts,
                selectedOption = state.district,
                onOptionSelected = {
                    calculatorViewModel.saveField("district", it)
                },
                readOnly = state.state.isBlank(),
                placeholder = "District *"
            )

            SelectorField(
                options = sssyData.crops,
                selectedOption = state.crop,
                onOptionSelected = {
                    calculatorViewModel.saveField("crop", it)
                },
                readOnly = state.district.isBlank(),
                placeholder = "Crop *"
            )

            LabeledTextField(
                value = state.area,
                onValueChange = {
                    calculatorViewModel.saveField("area", it)
                },
                keyboardType = KeyboardType.Number,
                label = "Area (In hectares) *",
                readOnly = state.crop.isBlank()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Button(
                    onClick = {
                        calculatorViewModel.resetValues()
                        showDetails = true
                    }
                ){
                    Text("Rest")
                }

                Button(
                    onClick = {
                        calculatorViewModel.calculate()
                        showDetails = true
                    },
                    enabled = state.crop.isNotEmpty()
                ){
                    Text("Calculate")
                }
            }


            if(showDetails && premiumCalculatorState != null){
                ModalBottomSheet(
                    onDismissRequest = {
                        showDetails = false
                    }
                ) {
                    premiumCalculatorState?.let { PremiumDetails(it) }
                }
            }


        }

    }

}

