package com.ar.farmguard.core.presentation

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
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


fun getDayOfWeekFromTimestamp(timestamp: Long,): String {

    val localDateTime = timestamp.toTimeStamp()

    val dayOfWeek: DayOfWeek = localDateTime.dayOfWeek
    println("DayOfWeek: $dayOfWeek")

    return dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
}


fun formatTimestampToCustomString(timestamp: Long): String {
    val localDateTime = timestamp.toTimeStamp()

    val customFormat = LocalDateTime.Format{
        date(
            LocalDate.Format {
                dayOfMonth()
                char(' ')
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                chars(" ")
                year()
            }
        )
    }

    return localDateTime.format(customFormat)
}

fun shouldRequestNewWeather(lastRequestTimeInSeconds: Long?, difference: Int = 15): Boolean {
    println("differenceInMinutes: $lastRequestTimeInSeconds")
    if(lastRequestTimeInSeconds == null){
        return false
    }

    val lastRequestTime = Instant.fromEpochSeconds(lastRequestTimeInSeconds)

    val currentTime = Clock.System.now()

    val differenceInMillis = currentTime.toEpochMilliseconds() - lastRequestTime.toEpochMilliseconds()

    val differenceInMinutes = differenceInMillis / (1000 * 60)

    println("differenceInMinutes: $differenceInMinutes")

    return differenceInMinutes >= difference
}