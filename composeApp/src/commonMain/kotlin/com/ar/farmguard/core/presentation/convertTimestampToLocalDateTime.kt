package com.ar.farmguard.core.presentation

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toTimeStamp(): LocalDateTime {
    val instant = Instant.fromEpochSeconds(this)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}
