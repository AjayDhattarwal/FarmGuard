package com.ar.farmguard.services.insurance.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.app.presentation.navigation.SignUpDestination
import com.ar.farmguard.app.presentation.navigation.SubGraph
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.ResidentialViewModel
import com.ar.farmguard.core.presentation.shared.components.StartupTopBar


@Composable
fun ResidentialDetails(
    residentialViewModel: ResidentialViewModel,
    navigate: (Any) -> Unit
) {

    val stateList by residentialViewModel.states.collectAsStateWithLifecycle()
    val districtList by residentialViewModel.districts.collectAsStateWithLifecycle()
    val subDistrict by residentialViewModel.subDistricts.collectAsStateWithLifecycle()
    val villageList by residentialViewModel.villages.collectAsStateWithLifecycle()

    var address by remember { mutableStateOf("" ) }
    var pinCode by remember { mutableStateOf("") }

    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedSubDistrict by remember { mutableStateOf("") }
    var selectedVillage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            StartupTopBar(
                title = "Sign Up",
                buttonTitle = "Login",
                onLoginClick = {
                    navigate(SubGraph.Login)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    SelectorField(
                        options = stateList,
                        selectedOption = selectedState,
                        onOptionSelected = {
                            selectedState = it
                        },
                        placeholder = "State *"
                    )
                }


                item{
                    SelectorField(
                        options = districtList,
                        selectedOption = selectedDistrict,
                        onOptionSelected = {
                            selectedDistrict = it
                        },
                        placeholder = "Gender*"
                    )

                }


                item{
                    SelectorField(
                        options = subDistrict,
                        selectedOption = selectedSubDistrict,
                        onOptionSelected = {
                            selectedSubDistrict = it
                        },
                        placeholder = "Cast Category"
                    )
                }


                item {
                    SelectorField(
                        options = villageList,
                        selectedOption = selectedVillage,
                        onOptionSelected = {
                            selectedVillage = it
                        },
                        placeholder = "Farmer category"
                    )

                }

                item{
                    LabeledTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Address *",
                        keyboardType = KeyboardType.Text
                    )
                }



                item{
                    LabeledTextField(
                        value = pinCode,
                        onValueChange = {
                            val maxLength = 6
                            pinCode = it.take(maxLength)
                        },
                        label = "Pin Code *",
                        keyboardType = KeyboardType.Phone
                    )

                }


                item{
                    MultiPageNavigation(
                        currentPage = 1,
                        onNext = {
                            residentialViewModel.saveResidentialDetails()
                            navigate(SignUpDestination.FarmerID)
                        }
                    )
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}
