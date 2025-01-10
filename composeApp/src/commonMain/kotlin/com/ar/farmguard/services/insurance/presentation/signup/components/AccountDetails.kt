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
import com.ar.farmguard.app.presentation.navigation.SubGraph
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.AccountViewModel
import com.ar.farmguard.core.presentation.shared.components.StartupTopBar

@Composable
fun AccountDetails(
    accountViewModel: AccountViewModel,
    navigate: (Any) -> Unit
) {

    val stateList by accountViewModel.states.collectAsStateWithLifecycle()
    val districtList by accountViewModel.districts.collectAsStateWithLifecycle()
    val bankList by accountViewModel.banks.collectAsStateWithLifecycle()
    val branchList by accountViewModel.branches.collectAsStateWithLifecycle()

    val isIfscAvailable by remember{ mutableStateOf(false) }
    var selectedIFSC by remember { mutableStateOf("") }
    var accountNo by remember { mutableStateOf("") }
    var confirmAccountNo by remember { mutableStateOf("") }

    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("") }
    var selectedBranch by remember { mutableStateOf("") }

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
                item{
                    LabeledTextField(
                        value = selectedIFSC,
                        onValueChange = { selectedIFSC = it },
                        label = "IFSC Code*",
                        keyboardType = KeyboardType.Text,
                        readOnly = !isIfscAvailable
                    )

                }


                item {
                    SelectorField(
                        options = stateList,
                        selectedOption = selectedState,
                        onOptionSelected = {
                            selectedState = it
                        },
                        placeholder = "State *",
                        readOnly = isIfscAvailable
                    )
                }


                item{
                    SelectorField(
                        options = districtList,
                        selectedOption = selectedDistrict,
                        onOptionSelected = {
                            selectedDistrict = it
                        },
                        placeholder = "Gender*",
                        readOnly = isIfscAvailable
                    )

                }


                item{
                    SelectorField(
                        options = bankList,
                        selectedOption = selectedBank,
                        onOptionSelected = {
                            selectedBranch = it
                        },
                        placeholder = "Cast Category",
                        readOnly = isIfscAvailable
                    )
                }


                item {
                    SelectorField(
                        options = branchList,
                        selectedOption = selectedBranch,
                        onOptionSelected = {
                            selectedBranch = it
                        },
                        placeholder = "Farmer category",
                        readOnly = isIfscAvailable
                    )

                }


                item{
                    LabeledTextField(
                        value = accountNo,
                        onValueChange = {
                            val maxLength = 18
                            accountNo = it.take(maxLength)
                        },
                        label = "Savings Bank A/C No.*",
                        keyboardType = KeyboardType.Phone
                    )

                }

                item {
                    LabeledTextField(
                        value = confirmAccountNo,
                        onValueChange = {
                            val maxLength = 18
                            confirmAccountNo = it.take(maxLength)
                        },
                        label = "Confirm A/C No.*",
                        keyboardType = KeyboardType.Phone
                    )
                }



                item{
                    MultiPageNavigation(
                        currentPage = 1,
                        onNext = {
                            accountViewModel.saveFarmerAccount()
                            navigate(SubGraph.Home)
                        }
                    )
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}
