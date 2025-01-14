package com.ar.farmguard.services.insurance.auth.login

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

    private val json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val _captcha = MutableStateFlow(byteArrayOf())
    val captcha = _captcha.asStateFlow()

    private val _isOtpRequested = MutableStateFlow(false)
    val isOtpRequested = _isOtpRequested.asStateFlow()

    private val _isOtpVerifying = MutableStateFlow(false)
    val isOtpVerifying = _isOtpVerifying.asStateFlow()

    private val _message = MutableStateFlow<Message?>(null)
    val message = _message.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    private val _isLogin = MutableStateFlow(false)
    val isLogin = _isLogin.asStateFlow()

    init {
        checkUserLoginState()
    }

    fun checkUserLoginState(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val response = authRepository.checkLoginState()
                if(response.first){
                    _isLogin.value = true
                }else{
                    _isLogin.value = false
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
                        _message.value = Message(
                            key = MessageKey.CAPTCHA_INFO,
                            string = captchaResponse.error,
                            status = MessageStatus.ERROR
                        )
                        l.i{ "getCaptcha: Error fetching captcha: ${captchaResponse.error}"}
                        return@withContext
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
                    _message.value = Message(
                        key = MessageKey.MOBILE_NO_INFO,
                        string = "Invalid Mobile Number",
                        status = MessageStatus.ERROR
                    )
                    return@withContext
                }

                _isOtpRequested.value = true

                val response = authRepository.getOtp(number,captcha)
                _message.value = response.second

                if(response.first){
                    _isOtpRequested.value = true
                }else{
                    _isOtpRequested.value = false
                }

            }
        }
    }

    fun loginUser(phoneNumber: String, otp: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isOtpVerifying.value = true

                val response = authRepository.verifyOtp(phoneNumber.toLong(), otp.toLong())
                _message.value = response.second

                if(response.first){
                    _success.value = true
                }

            }
        }

    }


}