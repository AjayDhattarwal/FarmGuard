package com.ar.farmguard.core.presentation

import com.ar.farmguard.app.presentation.util.UiText
import com.ar.farmguard.core.domain.DataError
import farmguard.composeapp.generated.resources.Res
import farmguard.composeapp.generated.resources.conflict_error
import farmguard.composeapp.generated.resources.disk_full_error
import farmguard.composeapp.generated.resources.no_internet_error
import farmguard.composeapp.generated.resources.payload_too_large_error
import farmguard.composeapp.generated.resources.request_timeout_error
import farmguard.composeapp.generated.resources.serialization_error
import farmguard.composeapp.generated.resources.server_error
import farmguard.composeapp.generated.resources.too_many_requests_error
import farmguard.composeapp.generated.resources.unauthorized_error
import farmguard.composeapp.generated.resources.unknown_error
import farmguard.composeapp.generated.resources.unknown_local_error
import farmguard.composeapp.generated.resources.unknown_remote_error

fun DataError.toUiText() : UiText{

    val stringRes = when (this) {
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.request_timeout_error
        DataError.Remote.UNAUTHORIZED -> Res.string.unauthorized_error
        DataError.Remote.CONFLICT -> Res.string.conflict_error
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.too_many_requests_error
        DataError.Remote.NO_INTERNET -> Res.string.no_internet_error
        DataError.Remote.PAYLOAD_TOO_LARGE -> Res.string.payload_too_large_error
        DataError.Remote.SERIALIZATION -> Res.string.serialization_error
        DataError.Remote.UNKNOWN -> Res.string.unknown_remote_error
        DataError.Local.DISK_FULL -> Res.string.disk_full_error
        DataError.Local.UNKNOWN -> Res.string.unknown_local_error
        else -> Res.string.unknown_error
    }
    return UiText.StringResourceId(stringRes)
}