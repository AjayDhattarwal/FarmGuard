package com.ar.farmguard.core.presentation

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toTimeStamp(): LocalDateTime {
    val instant = Instant.fromEpochSeconds(this)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}


fun parseDate(createdAt: String): LocalDate {
    val parts = createdAt.split("-")
    return LocalDate(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
}