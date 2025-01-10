package com.ar.farmguard.app.networking.util

sealed interface DataError: Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN;
    }
    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}