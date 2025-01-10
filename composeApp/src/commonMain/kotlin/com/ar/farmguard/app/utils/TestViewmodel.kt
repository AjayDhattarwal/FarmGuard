package com.ar.farmguard.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch

class TestViewmodel : ViewModel() {

    val l = Logger.withTag("TestViewmodel")

    fun decrypt(){
        try{
            viewModelScope.launch {


            }
        } catch (e: Exception){
            l.e(e){"error is this in the decrypt :${e.message}"}
        }
    }
}