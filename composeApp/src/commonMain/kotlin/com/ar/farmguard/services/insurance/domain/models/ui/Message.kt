package com.ar.farmguard.services.insurance.domain.models.ui

import androidx.compose.ui.graphics.Color

data class Message(
    val key : MessageKey,
    val string: String,
    val status : MessageStatus
){
    val color = when(status){
        MessageStatus.ERROR -> Color.Red
        MessageStatus.SUCCESS -> Color.Green
        MessageStatus.INFO -> Color.Blue
    }
}

enum class MessageKey {
    LOGIN_ERROR,
    LOGIN_SUCCESS,
    LOGIN_INFO,
    SIGNUP_ERROR,
    SIGNUP_SUCCESS,
    SIGNUP_INFO,
    OTP_REQUEST,
    OTP_INFO,
    CAPTCHA_INFO,
    OTP_VERIFICATION_ERROR,
    OTP_VERIFICATION_SUCCESS,
    OTP_VERIFICATION_INFO,
    MOBILE_NO_INFO,
    USER_CREATION_ERROR,
    USER_CREATION_SUCCESS,
    USER_CREATION_INFO,
    INVALID_MOBILE_NUMBER,

}

enum class MessageStatus{
    ERROR,
    SUCCESS,
    INFO
}