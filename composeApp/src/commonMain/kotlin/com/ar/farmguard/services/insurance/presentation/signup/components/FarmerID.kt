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
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.core.presentation.shared.components.StartupTopBar

@Composable
fun FarmerID(
    farmerViewModel: FarmerViewModel,
    navigate: (Any) -> Unit
) {

    val farmerIDList by farmerViewModel.farmerIdList.collectAsStateWithLifecycle()

    var selectedIdType by remember { mutableStateOf("" ) }
    var idNo by remember { mutableStateOf("") }


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
                        options = farmerIDList,
                        selectedOption = selectedIdType,
                        onOptionSelected = {
                            selectedIdType = it
                        },
                        placeholder = "Select ID Type*"
                    )
                }

                item{
                    LabeledTextField(
                        value = idNo,
                        onValueChange = {
                            val maxLength = 12
                            idNo = it.take(maxLength)
                        },
                        label = if(selectedIdType.isNotBlank()) "$selectedIdType No.*" else "ID No.*",
                        keyboardType = KeyboardType.Phone
                    )

                }

                item{
                    MultiPageNavigation(
                        currentPage = 1,
                        onNext = {
                            farmerViewModel.saveFarmerID()
                            navigate(SignUpDestination.AccountDetails)
                        }
                    )
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}
