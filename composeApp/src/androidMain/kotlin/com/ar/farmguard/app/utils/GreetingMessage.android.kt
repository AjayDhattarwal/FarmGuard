package com.ar.farmguard.app.utils

import java.util.Calendar

actual fun currentHour(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY)
}