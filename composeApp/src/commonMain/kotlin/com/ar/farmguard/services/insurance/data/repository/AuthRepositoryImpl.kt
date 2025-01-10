package com.ar.farmguard.services.insurance.data.repository

import com.ar.farmguard.services.insurance.data.network.AuthServiceImpl
import com.ar.farmguard.services.insurance.domain.models.remote.CaptchaResponse
import com.ar.farmguard.services.insurance.domain.repository.AuthRepository
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readRawBytes
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val authService: AuthServiceImpl
): AuthRepository {


    override suspend fun checkLoginState(): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = authService.fetchLoginState()
            if(response.status.isSuccess()){
                Pair(true, response.bodyAsText())
            }else{
                Pair(false, response.status.description)
            }
        } catch (e: Exception){
            e.printStackTrace()
            Pair(false, e.message ?: "exception")
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

    override suspend fun getOtp(phoneNumber: Long, captcha: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        return@withContext try {

            val response = authService.getOtp(phoneNumber, captcha)

            if(response.status.isSuccess()){
                Pair(true, response.bodyAsText())
            }else{
                Pair(false, response.status.description)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, e.message ?: "exception" )
        }
    }

    override suspend fun loginUser(phoneNumber: Long, otp: Long): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        return@withContext try {

            val response = authService.loginUser(phoneNumber, otp)

            if(response.status.isSuccess()){
                Pair(true, response.bodyAsText())
            }else{
                Pair(false, response.status.description)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, e.message ?: "exception" )
        }
    }



}