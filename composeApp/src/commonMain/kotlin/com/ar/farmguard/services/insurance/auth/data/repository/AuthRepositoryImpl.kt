package com.ar.farmguard.services.insurance.auth.data.repository

import co.touchlab.kermit.Logger
import com.ar.farmguard.app.utils.StringType
import com.ar.farmguard.app.utils.checkType
import com.ar.farmguard.app.utils.deserializeString
import com.ar.farmguard.services.insurance.auth.data.network.AuthService
import com.ar.farmguard.services.insurance.auth.domain.models.remote.CaptchaResponse
import com.ar.farmguard.services.insurance.auth.domain.models.remote.LoginCheckResponse
import com.ar.farmguard.services.insurance.auth.domain.models.remote.LoginResponse
import com.ar.farmguard.services.insurance.auth.domain.models.remote.OtpResponse
import com.ar.farmguard.services.insurance.auth.domain.models.remote.UserData
import com.ar.farmguard.services.insurance.auth.domain.models.ui.Message
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageKey
import com.ar.farmguard.services.insurance.auth.domain.models.ui.MessageStatus
import com.ar.farmguard.services.insurance.auth.domain.repository.AuthRepository
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readRawBytes
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {

    private val json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val l = Logger.withTag("AuthRepository")

    override suspend fun checkLoginState(): Pair<Boolean, LoginCheckResponse?> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = authService.fetchLoginState()
            if(response.status.isSuccess()){

                val responseString = response.bodyAsText()

                val response = try {
                    val responseType = responseString.checkType()
                    when(responseType){
                        StringType.JSON -> json.decodeFromString<LoginCheckResponse>(responseString)
                        StringType.HEX -> responseString.deserializeString<LoginCheckResponse>()
                        else -> LoginCheckResponse(status = false, error = responseString)
                    }
                } catch (e: Exception){
                    LoginCheckResponse(status = false,error = "${e.message}")
                }

                Pair(response.status, response)

            }else{
                Pair(false, null)
            }
        }catch (e: Exception){
            Pair(false, null)
        }
    }

    override suspend fun getCaptcha(): CaptchaResponse =  withContext(Dispatchers.IO)  {
        return@withContext try {
            val response = authService.getCaptcha()

            if (response.status.isSuccess()) {

                val rawBytes = response.readRawBytes()

                Json.decodeFromString(rawBytes.decodeToString())

            } else {
                CaptchaResponse(status = false, error = response.status.description, data = "")
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            CaptchaResponse(status = false, error = e.message ?: "exception", data = "")
        }
    }

    override suspend fun getOtp(phoneNumber: Long, captcha: String, verifyOnly: Boolean): Pair<Boolean, Message> = withContext(Dispatchers.IO) {
        return@withContext try {

            val response = authService.getOtp(phoneNumber, captcha, verifyOnly)
            val responseString = response.bodyAsText()
            l.i(responseString)
            if(response.status.isSuccess()) {


                val otpResponse = try {
                    val responseType = responseString.checkType()
                    when (responseType) {
                        StringType.JSON -> json.decodeFromString<OtpResponse>(responseString)
                        StringType.HEX -> responseString.deserializeString<OtpResponse>()
                        StringType.STRING -> OtpResponse(error = response.status.description)
                    }
                } catch (e: Exception) {
                    OtpResponse(error = "${e.message}")
                }

                l.i("$otpResponse")

                val message = if (otpResponse.status) {
                    Message(
                        key = MessageKey.OTP_REQUEST,
                        string = "Otp Send Successfully",
                        status = MessageStatus.SUCCESS
                    )
                } else {
                    Message(
                        key = MessageKey.OTP_REQUEST,
                        string = otpResponse.error,
                        status = MessageStatus.ERROR
                    )
                }

                Pair(otpResponse.status, message)
            }else {
                val message = Message(
                    key = MessageKey.OTP_REQUEST,
                    string = response.status.description,
                    status = MessageStatus.ERROR
                )

                Pair(false, message)
            }

        } catch (e: Exception) {
            val message =  Message(key = MessageKey.OTP_REQUEST, string = e.message ?: "exception", status = MessageStatus.ERROR)
            Pair(false, message)
        }
    }


    override suspend fun verifyOtp(phoneNumber: Long, otp: Long, verifyOnly: Boolean): Pair<Boolean, Message> = withContext(Dispatchers.IO) {
        return@withContext try {

            val response = authService.loginUser(phoneNumber, otp, verifyOnly)

            val responseString = response.bodyAsText()

            if(response.status.isSuccess()){

                val responseType = responseString.checkType()
                val loginResponse = try {
                    when(responseType){
                        StringType.JSON -> json.decodeFromString<LoginResponse>(responseString)
                        StringType.HEX -> responseString.deserializeString<LoginResponse>()
                        else -> LoginResponse(user = UserData(error = responseString))
                    }
                }catch (e: Exception){
                    LoginResponse(user = UserData(error = "${e.message}"))
                }

                l.i("$loginResponse")

                val user = loginResponse.user

                val message = if(user?.status == true){
                    Message(
                        key = MessageKey.OTP_INFO,
                        string = "Otp Verification Success ",
                        status = MessageStatus.SUCCESS
                    )
                }else{
                    Message(
                        key = MessageKey.OTP_INFO,
                        string = user?.error ?: "Otp Verification Failed",
                        status = MessageStatus.ERROR
                    )
                }

                Pair(user?.status==true, message)

            }else{
                val message = Message(
                    key = MessageKey.OTP_INFO,
                    string = response.status.description,
                    status = MessageStatus.ERROR
                )
                Pair(false, message )
            }
        } catch (e: Exception) {

            val message = Message(
                key = MessageKey.OTP_INFO,
                string = e.message.toString() ,
                status = MessageStatus.ERROR
            )

            Pair(false, message)
        }
    }



}