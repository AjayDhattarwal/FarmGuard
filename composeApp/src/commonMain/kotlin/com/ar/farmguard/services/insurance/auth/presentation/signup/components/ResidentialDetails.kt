package com.ar.farmguard.services.insurance.auth.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.core.presentation.navigation.ServiceDestination
import com.ar.farmguard.core.presentation.navigation.SignUpDestination
import com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel.ResidentialViewModel
import com.ar.farmguard.core.presentation.shared.components.TopBarWithMenu
import kotlin.reflect.KFunction0


@Composable
fun ResidentialDetails(
    residentialViewModel: ResidentialViewModel,
    navigate: (Any) -> Unit,
    onBackPress: KFunction0<Unit>
) {

    val stateList by residentialViewModel.states.collectAsStateWithLifecycle()
    val districtList by residentialViewModel.districts.collectAsStateWithLifecycle()
    val subDistrict by residentialViewModel.subDistricts.collectAsStateWithLifecycle()
    val villageList by residentialViewModel.villages.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    var address by remember { mutableStateOf("" ) }
    var pinCode by remember { mutableStateOf("") }

    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedSubDistrict by remember { mutableStateOf("") }
    var selectedVillage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarWithMenu(
                title = "Sign Up",
                onBackPress = onBackPress,
                dropDownMenuOptions = listOf("Login"),
                onMenuItemSelected = { index ->
                    when(index){
                        0 -> navigate(ServiceDestination.Login())
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(10.dp))

            Text(
                text = "Residential Details:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            SelectorField(
                options = stateList,
                selectedOption = selectedState,
                onOptionSelected = {
                    selectedState = it
                },
                placeholder = "State *"
            )


            SelectorField(
                options = districtList,
                selectedOption = selectedDistrict,
                onOptionSelected = {
                    selectedDistrict = it
                },
                placeholder = "District *"
            )


            SelectorField(
                options = subDistrict,
                selectedOption = selectedSubDistrict,
                onOptionSelected = {
                    selectedSubDistrict = it
                },
                placeholder = "Sub District *"
            )


            SelectorField(
                options = villageList,
                selectedOption = selectedVillage,
                onOptionSelected = {
                    selectedVillage = it
                },
                placeholder = "Village *"
            )

            LabeledTextField(
                value = address,
                onValueChange = { address = it },
                label = "Address *",
                keyboardType = KeyboardType.Text
            )

            LabeledTextField(
                value = pinCode,
                onValueChange = {
                    val pinCodeMaxLength = 6
                    pinCode = it.take(pinCodeMaxLength)
                },
                label = "Pin Code *",
                keyboardType = KeyboardType.Phone
            )

            MultiPageNavigation(
                onNext = {
                    residentialViewModel.saveResidentialDetails()
                    navigate(SignUpDestination.FarmerID)
                }
            )

            Spacer(Modifier.height(100.dp))
        }
    }

}
