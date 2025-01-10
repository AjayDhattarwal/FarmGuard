package com.ar.farmguard.services.insurance.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.farmguard.services.insurance.domain.models.ui.Message
import com.ar.farmguard.services.insurance.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.domain.models.ui.MessageStatus
import com.ar.farmguard.app.presentation.navigation.SubGraph
import com.ar.farmguard.services.insurance.presentation.login.components.CaptchaSvgDisplay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    navigate: (Any) -> Unit
){

    val captchaData by loginViewModel.captcha.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val messageData by loginViewModel.message.collectAsStateWithLifecycle()
    val isOtpRequested by loginViewModel.isOtpRequested.collectAsStateWithLifecycle()
    val isOtpVerifying by loginViewModel.isOtpVerifying.collectAsStateWithLifecycle()
    val success by loginViewModel.success.collectAsStateWithLifecycle()


    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        MobileVerifyUI(
            captcha = { captchaData },
            title = "Login With Otp",
            launchLambda = {
                loginViewModel.getCaptcha()
            },
            refreshCaptcha = {
                loginViewModel.getCaptcha()
            },
            requestOtp = { phoneNumber, captcha ->
                loginViewModel.getOtp(phoneNumber, captcha)
            },
            verifyOtp = { phoneNumber, otp ->
                loginViewModel.loginUser(phoneNumber, otp)
            },
            verifyButtonText = "Login",
            messageData = {
                messageData
            },
            isOtpRequesting = {
                isOtpRequested
            },
            isOtpVerifying = {
                isOtpVerifying
            },
            isValidated = {
                success
            },
            validateCallback = {
                scope.launch {
                    delay(2000L)
                    navigate(SubGraph.Home)
                }
            }


        )
    }


}


@Composable
fun MobileVerifyUI(
    modifier: Modifier = Modifier,
    mobileNo: String = "",
    captcha: () -> String,
    title: String,
    verifyButtonText: String = "Verify",
    hideNumber: Boolean = false,
    spacerHeight: Int = 32,
    launchLambda: () -> Unit = {},
    refreshCaptcha: () -> Unit = {},
    requestOtp: (String, String) -> Unit = {_,_ ->},
    verifyOtp: (String, String) -> Unit = {_,_ ->},
    messageData: () -> Message? = {null},
    isOtpRequesting: () -> Boolean = {false},
    isOtpVerifying: () -> Boolean = {false},
    isValidated: () -> Boolean = {false},
    validateCallback: () -> Unit = {},
){
    var mobileNumber by remember { mutableStateOf(mobileNo) }
    var captchaText by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var message by remember(messageData()) { mutableStateOf(messageData()) }
    val mobileError by remember {
        derivedStateOf{
            (mobileNumber.length != 10) &&
                    message?.key == MessageKey.MOBILE_NO_INFO &&
                    message?.status == MessageStatus.ERROR
        }
    }

    var isTimerFinished by remember { mutableStateOf(false) }



    LaunchedEffect(Unit){
        launchLambda.invoke()
    }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        if(isValidated()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text("SuccessFully $verifyButtonText")
                validateCallback()
            }
        } else{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )

                if(!hideNumber){
                    Spacer(modifier = Modifier.height(spacerHeight.dp))

                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = {
                            mobileNumber = it.take(10)
                            if(mobileNumber.length == 10 && message?.key == MessageKey.MOBILE_NO_INFO){
                                message = null
                            }
                        },
                        label = { Text("Mobile Number") },
                        placeholder = { Text("Enter 10-digit mobile number") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isOtpRequesting(),
                        isError =  mobileError
                    )
                    if(message?.key == MessageKey.MOBILE_NO_INFO && message != null){
                        Text(
                            text = message!!.string,
                            color = message!!.color,
                            maxLines = 3
                        )
                    }
                }

                Spacer(modifier = Modifier.height((spacerHeight/2).dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {

                    CaptchaSvgDisplay(modifier = Modifier.weight(1f), svgData = captcha)

                    IconButton(onClick = refreshCaptcha) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh Captcha",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = captchaText,
                    onValueChange = {
                        captchaText = it
                        if(message?.key == MessageKey.CAPTCHA_INFO){
                            message = null
                        }
                    },
                    label = { Text("Enter Captcha") },
                    placeholder = { Text("Enter displayed captcha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isOtpRequesting(),
                    isError = (message?.key == MessageKey.CAPTCHA_INFO) && (message?.status == MessageStatus.ERROR)
                )
                if(message?.key == MessageKey.CAPTCHA_INFO && message != null){
                    Text(
                        text = message!!.string,
                        color = message!!.color,
                        maxLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if(message?.key == MessageKey.OTP_REQUEST && message != null) {
                    Text(
                        text = message!!.string,
                        color = message!!.color,
                        maxLines = 3
                    )
                }

                if(!isOtpRequesting()){
                    Button(
                        onClick = {
                            if(captchaText.isNotEmpty() && mobileNumber.length == 10){
                                requestOtp(
                                    mobileNumber,
                                    captchaText
                                )
                                message = null
                            }else{
                                if(mobileNumber.length != 10){
                                    message = Message(
                                        key = MessageKey.MOBILE_NO_INFO,
                                        string = "Please Enter Valid Mobile Number",
                                        status = MessageStatus.ERROR
                                    )
                                }else{
                                    message = Message(
                                        key = MessageKey.CAPTCHA_INFO,
                                        string = "Please Enter Captcha",
                                        status = MessageStatus.ERROR
                                    )
                                }
                            }
                        },
                        enabled = !isOtpRequesting(),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if(!isOtpRequesting()){
                            Text("Request OTP")
                        }else{
                            Text("Requesting OTP...")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (isOtpRequesting()) {
                    Box{
                        OutlinedTextField(
                            value = otp,
                            onValueChange = {
                                otp = it.take(6)
                            },
                            label = { Text("Enter OTP") },
                            placeholder = { Text("Enter the OTP received") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            enabled = !isOtpVerifying(),
                            modifier = Modifier.fillMaxWidth(),
                            isError = (message?.key == MessageKey.OTP_INFO) && (message?.status == MessageStatus.ERROR)
                        )

                        Box(Modifier.align(Alignment.CenterEnd)){
                            if (isTimerFinished) {
                                TextButton(
                                    onClick = {
                                        if (captchaText.isNotEmpty() && mobileNumber.length == 10) {
                                            requestOtp(
                                                mobileNumber,
                                                captchaText
                                            )
                                            message = null
                                            isTimerFinished = false
                                        }
                                    }
                                ) {
                                    Text("Resend Otp", style = MaterialTheme.typography.labelSmall)
                                }
                            } else {
                                OTPRequestTimer { isTimerFinished = true }
                            }
                        }
                    }


                    if(message?.key == MessageKey.OTP_INFO && message != null) {
                        Text(
                            text = message!!.string,
                            color = message!!.color,
                            maxLines = 3
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if(otp.length == 6){
                                verifyOtp(mobileNumber, otp)
                                if(message?.status == MessageStatus.ERROR){
                                    message = null
                                }
                            }else{
                                message = Message(
                                    key = MessageKey.OTP_INFO,
                                    string = "Invalid OTP",
                                    status = MessageStatus.ERROR
                                )
                            }
                        },
                        enabled = !isOtpVerifying() ,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if(!isOtpVerifying()){
                            Text(verifyButtonText)
                        }else{
                            Text("Verifying OTP...")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OTPRequestTimer(onTimerFinish: () -> Unit) {
    var timeLeft by remember { mutableStateOf(60) }

    LaunchedEffect(timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        }
        onTimerFinish()
    }

    TextButton(onClick = {}){
        Text(text = "0:${timeLeft}s", style = MaterialTheme.typography.labelSmall)
    }
}
