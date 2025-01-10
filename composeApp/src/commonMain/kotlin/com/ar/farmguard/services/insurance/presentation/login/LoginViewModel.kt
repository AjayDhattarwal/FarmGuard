package com.ar.farmguard.services.insurance.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.services.insurance.domain.models.remote.LoginResponse
import com.ar.farmguard.services.insurance.domain.models.remote.OtpResponse
import com.ar.farmguard.services.insurance.domain.models.ui.Message
import com.ar.farmguard.services.insurance.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.domain.repository.AuthRepository
import com.ar.farmguard.app.utils.deserializeString
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



    init {
        getCaptcha()
    }

    fun getCaptcha(tryCount: Int = 3){

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val captchaResponse = authRepository.getCaptcha()

                _captcha.value = captchaResponse.data

                if (captchaResponse.status) {
                    _captcha.value = captchaResponse.data

                } else {
                    if (tryCount > 0) {
                        delay(1000)
                        getCaptcha(tryCount - 1)
                    } else {
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
                if(number != null){
                    _isOtpRequested.value = true
                    val response = authRepository.getOtp(number,captcha)

                    l.i{ "getOtp: ${response}"}

                    if(response.first){

                        val otpResponse =  try{
                            response.second.deserializeString<OtpResponse>()
                        } catch (e: Exception){
                            val json = Json{
                                ignoreUnknownKeys = true
                            }
                            json.decodeFromString<OtpResponse>(response.second)
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
                    else{
                        _message.value = Message(
                            key = MessageKey.OTP_REQUEST,
                            string = response.second,
                            status = MessageStatus.ERROR
                        )
                        _isOtpRequested.value = false
                    }
                }else{
                    _message.value = Message(
                        key = MessageKey.MOBILE_NO_INFO,
                        string = "Invalid Mobile Number",
                        status = MessageStatus.ERROR
                    )
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

                if(pair.first){
                    val loginResponse =  try{
                        Json.decodeFromString<LoginResponse>(pair.second)
                    } catch (e: Exception){
                        pair.second.deserializeString<LoginResponse>()
                    }
                    l.i("$loginResponse")
                    _message.value = Message(
                        key = MessageKey.OTP_INFO,
                        string = "Otp Verification Success ",
                        status = MessageStatus.SUCCESS
                    )
                }


                if(!pair.first){
                    _isOtpVerifying.value = false
                } else{
                    _success.value = pair.first
                }
                l.i{ "loginUser: ${pair.second}"}
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