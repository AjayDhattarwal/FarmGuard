package com.ar.farmguard.services.insurance.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.app.presentation.navigation.SignUpDestination
import com.ar.farmguard.app.presentation.navigation.SubGraph
import com.ar.farmguard.services.insurance.presentation.login.LoginScreen
import com.ar.farmguard.services.insurance.presentation.login.MobileVerifyUI
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.FarmerViewModel
import com.ar.farmguard.services.insurance.presentation.signup.viewmodel.SignUpViewModel
import com.ar.farmguard.core.presentation.shared.components.StartupTopBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun FarmerDetailsForm(
    farmerViewModel: FarmerViewModel,
    signUpViewModel: SignUpViewModel,
    navigate: (Any) -> Unit
) {
    val fullName by farmerViewModel.fullName.collectAsStateWithLifecycle()
    val passbookName by farmerViewModel.passbookName.collectAsStateWithLifecycle()
    val relativeName by farmerViewModel.relativeName.collectAsStateWithLifecycle()
    val relationship by farmerViewModel.relationship.collectAsStateWithLifecycle()
    val mobileNo by farmerViewModel.mobileNo.collectAsStateWithLifecycle()
    val age by farmerViewModel.age.collectAsStateWithLifecycle()
    val gender by farmerViewModel.gender.collectAsStateWithLifecycle()
    val castCategory by farmerViewModel.castCategory.collectAsStateWithLifecycle()
    val farmerCategory by farmerViewModel.farmerCategory.collectAsStateWithLifecycle()
    val farmerType by farmerViewModel.farmerType.collectAsStateWithLifecycle()

    val isVerified by signUpViewModel.isMobileVerified.collectAsStateWithLifecycle()

    var showVerifyDialog by remember{ mutableStateOf(false) }

    var selectedMobileNo by remember { mutableStateOf(mobileNo) }


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
                        value = fullName,
                        onValueChange = { farmerViewModel.saveFarmerDetails("fullName",it) },
                        label = "Full Name*",
                        keyboardType = KeyboardType.Text
                    )
                }

                item {
                    LabeledTextField(
                        value = passbookName,
                        onValueChange = { farmerViewModel.saveFarmerDetails("passbookName",it) },
                        label = "Passbook Name*",
                        keyboardType = KeyboardType.Text
                    )
                }

                item {
                    LabeledTextField(
                        value = relativeName,
                        onValueChange  = { farmerViewModel.saveFarmerDetails("relativeName",it) },
                        label = "Relative Name*",
                        keyboardType = KeyboardType.Text,
                    )
                }


                item {
                    SelectorField(
                        options = listOf("S/O", "D/O", "W/O", "C/O"),
                        selectedOption = relationship,
                        onOptionSelected = {
                           farmerViewModel.saveFarmerDetails("relationship",it)
                        },
                        placeholder = "Select Relationship*"
                    )
                }

                item{
                    Box{
                        LabeledTextField(
                            value = selectedMobileNo,
                            onValueChange = {
                                val maxLength = 10
                                selectedMobileNo = it.take(maxLength)
                            },
                            label = "Mobile No.*",
                            keyboardType = KeyboardType.Phone,
                        )

                        if(selectedMobileNo.length == 10){
                            TextButton(
                                enabled = !isVerified,
                                onClick = {
                                    showVerifyDialog = true
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ){
                                Text(
                                    text = if(isVerified) "Verified" else "Verify",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                }
                item {
                    LabeledTextField(
                        value = age,
                        onValueChange =  { newValue ->
                            val integer = newValue.toIntOrNull() ?: 0
                            if (integer in 0..99) {
                                farmerViewModel.saveFarmerDetails("age",newValue.take(2))
                            } else if (integer in 100..120) {
                                farmerViewModel.saveFarmerDetails("age", newValue.take(3))
                            }
                        },
                        label = "Age.*",
                        keyboardType = KeyboardType.Number
                    )
                }

                item{
                    SelectorField(
                        options = listOf("Male", "Female", "Others"),
                        selectedOption = gender,
                        onOptionSelected = {
                            farmerViewModel.saveFarmerDetails("gender", it)
                        },
                        placeholder = "Gender*"
                    )

                }


                item{
                    SelectorField(
                        options = listOf("General", "OBC", "SC", "ST"),
                        selectedOption = castCategory,
                        onOptionSelected = {
                            farmerViewModel.saveFarmerDetails("castCategory", it)
                        },
                        placeholder = "Cast Category"
                    )
                }


                item {
                    SelectorField(
                        options = listOf("Owner", "Tenant", "Share Cropper"),
                        selectedOption = farmerCategory,
                        onOptionSelected = {
                            farmerViewModel.saveFarmerDetails("farmerCategory", it)
                        },
                        placeholder = "Farmer category"
                    )

                }

                item {
                    SelectorField(
                        options = listOf("Small", "Marginal", "Others"),
                        selectedOption = farmerType,
                        onOptionSelected = {
                            farmerViewModel.saveFarmerDetails("farmerType", it)
                        },
                        placeholder = "Farmer Type"
                    )
                }

                item{
                    MultiPageNavigation(
                        currentPage = 1,
                        onNext = {
                            navigate(SignUpDestination.ResidentialDetail)
                        }
                    )
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }

    if(showVerifyDialog && !isVerified){
        MobileVerifyDialog(
            mobileNo = mobileNo,
            onDismissRequest = {
                showVerifyDialog = false
            },
            captchaData = {
                "1234"
            },
            launchLambda = {
                signUpViewModel.getCaptcha()
            },
            refreshCaptcha = {
                signUpViewModel.getCaptcha()
            },
            requestOtp = { phoneNumber, captcha ->
                signUpViewModel.getOtp(phoneNumber, captcha)
            },
            verifyOtp = { phoneNumber, otp ->
                signUpViewModel.verifyMobile(phoneNumber, otp)
                showVerifyDialog = false
            }
        )

    }

}



@Composable
fun MobileVerifyDialog(
    onDismissRequest: () -> Unit,
    mobileNo: String,
    captchaData: () -> String,
    description: String? = null,
    launchLambda: () -> Unit,
    refreshCaptcha: () -> Unit = {},
    requestOtp: (String, String) -> Unit = {_,_ ->},
    verifyOtp: (String, String) -> Unit = {_,_ ->}
){
    Dialog(
        onDismissRequest = onDismissRequest
    ){
        MobileVerifyUI(
            mobileNo = mobileNo,
            captcha =  captchaData,
            title = "Verify Mobile Number",
            launchLambda = launchLambda,
            hideNumber = true,
            refreshCaptcha = refreshCaptcha,
            requestOtp = requestOtp,
            verifyOtp = verifyOtp,
            verifyButtonText = "Login",
        )

    }
}











