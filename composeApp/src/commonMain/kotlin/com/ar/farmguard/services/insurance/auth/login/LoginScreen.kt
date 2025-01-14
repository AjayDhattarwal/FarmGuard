@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.ar.farmguard.services.insurance.auth.login


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ar.farmguard.core.presentation.navigation.SubGraph
import com.ar.farmguard.app.utils.PMFBY_IMG
import com.ar.farmguard.core.presentation.shared.components.TopBar
import com.ar.farmguard.services.insurance.auth.components.MobileVerifyUI
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LoginScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    loginViewModel: LoginViewModel = koinViewModel(),
    onBackPress: () -> Unit,
    navigate: (Any) -> Unit,
){

    val captchaData by loginViewModel.captcha.collectAsStateWithLifecycle()
    val messageData by loginViewModel.message.collectAsStateWithLifecycle()
    val isOtpRequested by loginViewModel.isOtpRequested.collectAsStateWithLifecycle()
    val isOtpVerifying by loginViewModel.isOtpVerifying.collectAsStateWithLifecycle()
    val success by loginViewModel.success.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()


    Scaffold (
        topBar = {
            TopBar(
                onBackPress = onBackPress
            )
        },
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,

        ){

            Spacer(Modifier.height(10.dp))

            with(sharedTransitionScope){
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(PMFBY_IMG)
                        .crossfade(true)
                        .placeholderMemoryCacheKey("image-PMFBY0")
                        .memoryCacheKey("image-PMFBY0")
                        .build(),
                    contentDescription = "PMFBY Logo",
                    modifier = Modifier.size(150.dp)
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(key = "image-PMFBY0"),
                            animatedVisibilityScope = animatedContentScope
                        )
                )
                Text(
                    text = "PMFBY",
                    modifier = Modifier.sharedBounds(
                        sharedTransitionScope.rememberSharedContentState(key = "text-PMFBY"),
                        animatedVisibilityScope = animatedContentScope
                    )
                )
            }


            MobileVerifyUI(
                captcha = { captchaData },
                title = "Login With Otp",
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
                    navigate(SubGraph.Home)
                }
            )
            Spacer(Modifier.height(140.dp))
        }

    }


}





