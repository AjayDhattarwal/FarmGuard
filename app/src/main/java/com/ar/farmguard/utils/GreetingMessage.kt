package com.ar.farmguard.utils

import java.time.LocalTime

fun greetingMessage(): String {
    val currentHour = LocalTime.now().hour
    val greeting = when {
        currentHour in 5..11 -> "Good morning"
        currentHour in 12..17 -> "Good afternoon"
        currentHour in 18..21 -> "Good evening"
        else -> "Hello"
    }
    return "$greeting👋"
}