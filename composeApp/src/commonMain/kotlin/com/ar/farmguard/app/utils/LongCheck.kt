package com.ar.farmguard.app.utils

fun LongCheck(phone: String): Long?{
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