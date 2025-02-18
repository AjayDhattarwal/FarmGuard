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
import com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.core.presentation.shared.components.TopBarWithMenu

@Composable
fun FarmerID(
    farmerViewModel: FarmerViewModel,
    navigate: (Any) -> Unit,
    onBackPress: () -> Unit,
) {

    val farmerIDList by farmerViewModel.farmerIdList.collectAsStateWithLifecycle()

    var selectedIdType by remember { mutableStateOf("" ) }
    var idNo by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()


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
                text = "Farmer ID:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )


            SelectorField(
                options = farmerIDList,
                selectedOption = selectedIdType,
                onOptionSelected = {
                    selectedIdType = it
                },
                placeholder = "Select ID Type*"
            )



            LabeledTextField(
                value = idNo,
                onValueChange = {
                    val maxLength = 12
                    idNo = it.take(maxLength)
                },
                label = if(selectedIdType.isNotBlank()) "$selectedIdType No.*" else "ID No.*",
                keyboardType = KeyboardType.Phone
            )




            MultiPageNavigation(
                onNext = {
                    farmerViewModel.saveFarmerID()
                    navigate(SignUpDestination.AccountDetails)
                }
            )
            Spacer(Modifier.height(100.dp))


        }
    }
}
