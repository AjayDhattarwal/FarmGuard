package com.ar.farmguard.app.utils


expect fun currentHour(): Int

fun greetingMessage(): String {
    val currentHour = currentHour()
    val greeting = when {
        currentHour in 5..11 -> "Good morning"
        currentHour in 12..17 -> "Good afternoon"
        currentHour in 18..21 -> "Good evening"
        else -> "Hello"
    }
    return "$greeting👋"
}