package com.ar.farmguard.services.insurance.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.app.utils.StringType
import com.ar.farmguard.app.utils.checkType
import com.ar.farmguard.services.insurance.domain.models.remote.LoginResponse
import com.ar.farmguard.services.insurance.domain.models.remote.OtpResponse
import com.ar.farmguard.services.insurance.domain.models.ui.Message
import com.ar.farmguard.services.insurance.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.domain.repository.AuthRepository
import com.ar.farmguard.app.utils.deserializeString
import com.ar.farmguard.services.insurance.domain.models.remote.UserData
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

    private var cookieExists = false

    private val json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val _captcha = MutableStateFlow("")
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
                    l.i{ "checkUserLoginState: ${response.second}"}
                    _isLogin.value = true
                }else{
                    _isLogin.value = false
                }
            }
        }
    }

    fun getCaptcha(tryCount: Int = 3){

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val captchaResponse = authRepository.getCaptcha()

                if (captchaResponse.status) {
                    _captcha.value = captchaResponse.data
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
                val number = longCheck(phoneNumber)

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

                l.i{ "getOtp: ${response}"}

                if(!response.first){

                    _message.value = Message(
                        key = MessageKey.OTP_REQUEST,
                        string = response.second,
                        status = MessageStatus.ERROR
                    )
                    _isOtpRequested.value = false

                    return@withContext

                }

                val otpResponse =  try{
                    val responseType = response.second.checkType()
                    when(responseType){
                        StringType.JSON -> json.decodeFromString<OtpResponse>(response.second)
                        StringType.HEX -> response.second.deserializeString<OtpResponse>()
                        StringType.STRING -> OtpResponse(error = response.second)
                    }
                } catch (e: Exception){
                    OtpResponse(error ="${e.message}")
                }

                l.i("$otpResponse")

                if(otpResponse.status){
                    _message.value = Message(
                        key = MessageKey.OTP_REQUEST,
                        string = "Otp Send Successfully",
                        status = MessageStatus.SUCCESS
                    )
                    _isOtpRequested.value = true
                }else{
                    _message.value = Message(
                        key = MessageKey.OTP_REQUEST,
                        string = otpResponse.error,
                        status = MessageStatus.ERROR
                    )
                    _isOtpRequested.value = false
                }


            }
        }
    }

    fun loginUser(phoneNumber: String, otp: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isOtpVerifying.value = true

                val pair = authRepository.loginUser(phoneNumber.toLong(), otp.toLong())

                l.i{ "loginUser: ${pair.second}"}

                if(!pair.first){
                    _isOtpVerifying.value = false
                    return@withContext
                }

                if(pair.first){

                    val responseType = pair.second.checkType()
                    val loginResponse = try {
                        when(responseType){
                            StringType.JSON -> json.decodeFromString<LoginResponse>(pair.second)
                            StringType.HEX -> pair.second.deserializeString<LoginResponse>()
                            else -> LoginResponse(user = UserData(error = pair.second))
                        }
                    }catch (e: Exception){
                        LoginResponse(user = UserData(error = "${e.message}"))
                    }

                    l.i("$loginResponse")

                    val user = loginResponse.user

                    if(user?.status == true){
                        _message.value = Message(
                            key = MessageKey.OTP_INFO,
                            string = "Otp Verification Success ",
                            status = MessageStatus.SUCCESS
                        )
                    }else{
                        _message.value = Message(
                            key = MessageKey.OTP_INFO,
                            string = user?.error ?: "Otp Verification Failed",
                            status = MessageStatus.ERROR
                        )
                    }

                    _success.value = user?.status == true
                }

            }
        }

    }


    private fun longCheck(phone: String): Long?{
        try {
            val value = phone.toLong()
            if(value.toString().length == 10){
                return value
            } else{
                return null
            }
        }catch (e: Exception){
            return null
        }
    }

}