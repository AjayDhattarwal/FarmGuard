package com.ar.farmguard.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ar.farmguard.services.insurance.domain.models.remote.AadhaarRequest
import kotlinx.coroutines.launch

class TestViewmodel : ViewModel() {

    val l = Logger.withTag("TestViewmodel")

    fun decrypt(){
        try{
            viewModelScope.launch {
//                val input = "886d7c11d64eb026c01197a4ff8bd70cecb08e08baa40c37632b5dcf5eae388e68ea715978d779399d526bbf71e831dedb2760860ced21393551b572a86529f170c95555335cb8760f20446b57711593167814488b0cf0f576d0a386629043817f20887947f3c0131946d8001d57324a9f4909f0160f8b744b07ba05e17cce2f"
//
//                try {
//                    val data = input.deserializeString<AadhaarRequest>()
//                    l.d { "decrypted text is $data" }
//                }catch (e:Exception){
//                    l.e(e){"error is this in the decrypt :${e.message}"}
//                }
            }
        } catch (e: Exception){
            l.e(e){"error is this in the decrypt :${e.message}"}
        }
    }
}