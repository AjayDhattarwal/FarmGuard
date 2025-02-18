package com.ar.farmguard.services.insurance.auth.presentation.signup.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel: ViewModel() {

    private val _isMobileVerified = MutableStateFlow(false)
    val isMobileVerified = _isMobileVerified.asStateFlow()

    private val _description = MutableStateFlow<Pair<Color, String>?>(null)
    val description = _description.asStateFlow()

    fun verifyMobile(mobileNo: String, otp: String){
        if(mobileNo.length == 10){
            _isMobileVerified.value = true
        } else {
            _description.value = Pair(Color.Red,"Mobile Number must be of 10 digits")
        }
    }

    fun getCaptcha() {

    }

    fun getOtp(phoneNumber: String, captcha: String){

    }


}