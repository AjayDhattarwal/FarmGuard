package com.ar.farmguard.app.utils

import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSDate

actual fun currentHour(): Int {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(NSCalendarUnitHour, NSDate())
    return components.hour.toInt()
}