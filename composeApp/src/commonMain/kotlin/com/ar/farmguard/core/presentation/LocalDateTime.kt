package com.ar.farmguard.core.presentation

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

fun Long.toTimeStamp(): LocalDateTime {
    val instant = Instant.fromEpochSeconds(this)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}


fun parseDate(createdAt: String): LocalDate {
    val parts = createdAt.replace(" 00:00:00", "").substringBefore(" ").split("-")
    return LocalDate(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
}

inline fun getTodayDate(): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return today.toString()
}

fun getDateSevenDaysBack(): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val sevenDaysBack = today.minus(DatePeriod(days = 7))
    return sevenDaysBack.toString()
}

fun getDay(data: String) : String{
    val dateInLocalDate = parseDate(data)
    val todayData = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    return when(dateInLocalDate){
        todayData -> "Today"
        todayData.minus(DatePeriod(days = 1)) -> "Yesterday"
        else -> dateInLocalDate.toString()
    }
}