package com.ar.farmguard.core.data

import com.ar.farmguard.core.domain.location.LocationProvider
import com.ar.farmguard.core.domain.location.LocationResult
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject
import kotlin.coroutines.suspendCoroutine
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.CoreLocation.kCLLocationAccuracyThreeKilometers
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class LocationProviderImpl : LocationProvider {

    private val locationManager = CLLocationManager()

    init {
//        locationManager.delegate = CLLocationManagerDelegateProtocol().
        locationManager.desiredAccuracy = kCLLocationAccuracyThreeKilometers
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getCurrentLocation(): LocationResult? = suspendCoroutine { continuation ->
        locationManager.delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = didUpdateLocations.firstOrNull() as? CLLocation
                if (location != null) {
                    val longitude = location.coordinate.useContents { longitude }
                    val latitude = location.coordinate.useContents { latitude }

                    continuation.resume(LocationResult(latitude, longitude))
                } else {
                    continuation.resume(null)
                }
                locationManager.delegate = null
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                continuation.resumeWithException(Exception("Failed to get location: ${didFailWithError.localizedDescription}, code: ${didFailWithError.code}"))
                locationManager.delegate = null
            }

            override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
                when (manager.authorizationStatus) {
                    kCLAuthorizationStatusAuthorizedWhenInUse, kCLAuthorizationStatusAuthorizedAlways -> {
                        locationManager.requestLocation()
                    }
                    kCLAuthorizationStatusDenied, kCLAuthorizationStatusRestricted -> {
                        continuation.resume(null)
                        locationManager.delegate = null
                    }
                    else -> return
                }
            }
        }

        when (locationManager.authorizationStatus) {
            kCLAuthorizationStatusNotDetermined -> locationManager.requestWhenInUseAuthorization()
            kCLAuthorizationStatusAuthorizedWhenInUse, kCLAuthorizationStatusAuthorizedAlways -> locationManager.requestLocation()
            else -> continuation.resume(null)
        }
    }


}