package com.ar.farmguard.core.domain.location

import com.ar.farmguard.Platform

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationResult?
}

