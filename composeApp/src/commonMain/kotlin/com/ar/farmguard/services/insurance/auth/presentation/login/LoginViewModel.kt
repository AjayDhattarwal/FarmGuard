package com.ar.farmguard.services.insurance.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.app.utils.LongCheck
import com.ar.farmguard.app.utils.StringType
import com.ar.farmguard.app.utils.checkType
import com.ar.farmguard.services.insurance.auth.domain.models.remote.LoginResponse
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.auth.domain.repository.AuthRepository
import com.ar.farmguard.app.utils.deserializeString
import com.ar.farmguard.services.insurance.auth.domain.models.remote.UserData
import com.ar.farmguard.services.insurance.auth.domain.models.ui.LoginState
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class LoginViewModel (
    private val authRepository: AuthRepository
): ViewModel() {

    private val l = Logger.withTag("LoginViewModel")

    private val _captcha = MutableStateFlow(byteArrayOf())
    val captcha = _captcha.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    init {
        checkUserLoginState()
    }

    private fun checkUserLoginState(){
        _loginState.value = _loginState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val response = authRepository.checkLoginState()
                if(response.first){
                    _loginState.value = _loginState.value.copy(
                        success = true,
                        isLoading = false
                    )
                }else{
                    _loginState.value = _loginState.value.copy(
                        isLoading = false
                    )
                    getCaptcha()
                }
            }
        }
    }

    fun getCaptcha(tryCount: Int = 3){

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val captchaResponse = authRepository.getCaptcha()

                if (captchaResponse.status) {
                    _captcha.value = captchaResponse.data.toByteArray()
                } else {
                    if (tryCount > 0) {
                        delay(5000)
                        getCaptcha(tryCount - 1)
                    } else {
                        _loginState.value = _loginState.value.copy(
                            message = Message(
                                key = MessageKey.CAPTCHA_INFO,
                                string = captchaResponse.error,
                                status = MessageStatus.ERROR
                            )
                        )
                    }
                }
            }
        }

    }


    fun getOtp(phoneNumber: String, captcha: String)  {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val number = LongCheck(phoneNumber)

                if(number == null) {
                    _loginState.value = _loginState.value.copy(
                        message = Message(
                            key = MessageKey.MOBILE_NO_INFO,
                            string = "Invalid Mobile Number",
                            status = MessageStatus.ERROR
                        )
                    )
                    return@withContext
                }

                _loginState.value = _loginState.value.copy(
                    isOtpRequested = true
                )

                val response = authRepository.getOtp(number,captcha)

                _loginState.value = _loginState.value.copy(
                    message = response.second
                )

                if(response.first){
                    _loginState.value = _loginState.value.copy(
                        isOtpRequested = true
                    )
                }else{
                    _loginState.value = _loginState.value.copy(
                        isOtpRequested = true
                    )
                }

            }
        }
    }

    fun loginUser(phoneNumber: String, otp: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _loginState.value = _loginState.value.copy(
                    isOtpVerifying = true
                )


                val response = authRepository.verifyOtp(phoneNumber.toLong(), otp.toLong())

                if(response.first){
                    _loginState.value = _loginState.value.copy(
                        isOtpVerifying = true,
                        message = response.second,
                        success = true
                    )
                }else{
                    _loginState.value = _loginState.value.copy(
                        isOtpVerifying = false,
                        message = response.second,
                    )
                }

            }
        }

    }


}