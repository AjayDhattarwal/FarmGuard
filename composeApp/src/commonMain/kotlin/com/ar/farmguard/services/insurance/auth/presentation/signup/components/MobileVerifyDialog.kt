package com.ar.farmguard.services.insurance.auth.presentation.signup.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.ar.farmguard.services.insurance.auth.presentation.components.MobileVerifyUI

@Composable
fun MobileVerifyDialog(
    onDismissRequest: () -> Unit,
    mobileNo: String,
    captchaData: () -> ByteArray,
    description: String? = null,
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
            hideNumber = true,
            refreshCaptcha = refreshCaptcha,
            requestOtp = requestOtp,
            verifyOtp = verifyOtp,
            verifyButtonText = "Login",
        )

    }
}