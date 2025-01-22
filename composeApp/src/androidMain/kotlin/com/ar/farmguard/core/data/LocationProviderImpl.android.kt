package com.ar.farmguard.core.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.ar.farmguard.core.domain.location.LocationProvider
import com.ar.farmguard.core.domain.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class LocationProviderImpl(
    private val context: Context
) : LocationProvider {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getCurrentLocation(): LocationResult? = suspendCoroutine { continuation ->
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            continuation.resume(null)
            return@suspendCoroutine
        }


        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                continuation.resume(LocationResult(location.latitude, location.longitude))
            } else {
                continuation.resumeWithException(Exception("Location not available"))
            }
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }

    }
}