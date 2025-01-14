package com.ar.farmguard.services.insurance.auth.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.ar.farmguard.core.presentation.navigation.SubGraph
import com.ar.farmguard.services.insurance.auth.signup.viewmodel.AccountViewModel
import com.ar.farmguard.core.presentation.shared.components.TopBarWithMenu

@Composable
fun AccountDetails(
    accountViewModel: AccountViewModel,
    navigate: (Any) -> Unit,
    onBackPress: () -> Unit,
) {

    val stateList by accountViewModel.states.collectAsStateWithLifecycle()
    val districtList by accountViewModel.districts.collectAsStateWithLifecycle()
    val bankList by accountViewModel.banks.collectAsStateWithLifecycle()
    val branchList by accountViewModel.branches.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

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
        },
        modifier = Modifier.imePadding(),
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .consumeWindowInsets(innerPadding)
                .verticalScroll(scrollState)
                .systemBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(10.dp))

            Text(
                text = "Account Details:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )


            LabeledTextField(
                value = selectedIFSC,
                onValueChange = { selectedIFSC = it },
                label = "IFSC Code*",
                keyboardType = KeyboardType.Text,
                readOnly = !isIfscAvailable
            )


            SelectorField(
                options = stateList,
                selectedOption = selectedState,
                onOptionSelected = {
                    selectedState = it
                },
                placeholder = "State *",
                readOnly = isIfscAvailable
            )

            SelectorField(
                options = districtList,
                selectedOption = selectedDistrict,
                onOptionSelected = {
                    selectedDistrict = it
                },
                placeholder = "District *",
                readOnly = isIfscAvailable
            )


            SelectorField(
                options = bankList,
                selectedOption = selectedBank,
                onOptionSelected = {
                    selectedBranch = it
                },
                placeholder = "Bank *",
                readOnly = isIfscAvailable
            )


            SelectorField(
                options = branchList,
                selectedOption = selectedBranch,
                onOptionSelected = {
                    selectedBranch = it
                },
                placeholder = "Branch *",
                readOnly = isIfscAvailable
            )


            LabeledTextField(
                value = accountNo,
                onValueChange = {
                    val maxLength = 18
                    accountNo = it.take(maxLength)
                },
                label = "Savings Bank A/C No.*",
                keyboardType = KeyboardType.Phone
            )


            LabeledTextField(
                value = confirmAccountNo,
                onValueChange = {
                    val maxLength = 18
                    confirmAccountNo = it.take(maxLength)
                },
                label = "Confirm A/C No.*",
                keyboardType = KeyboardType.Phone
            )



            MultiPageNavigation(
                isFinalPage = true,
                onNext = {
                    accountViewModel.saveFarmerAccount()
                    navigate(SubGraph.Home)
                }
            )

            Spacer(Modifier.height(100.dp))

        }

    }
}
