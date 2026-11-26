
package com.example.cosmicwindow.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.CancellationSignal
import android.os.SystemClock
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

data class GeoLocation(
    val latitude: Double,
    val longitude: Double,
)

class LocationProvider(
    private val context: Context,
) {
    suspend fun getLocation(): GeoLocation? {
        val permission =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED

        if (!permission) return null

        val manager =
            context.getSystemService(
                Context.LOCATION_SERVICE,
            ) as LocationManager

        val provider = LocationManager.FUSED_PROVIDER

        try {
            if (!manager.isLocationEnabled ||
                !manager.isProviderEnabled(provider)
            ) {
                return null
            }

            val cached = manager.getLastKnownLocation(provider)

            if (cached != null) {
                val age =
                    SystemClock.elapsedRealtimeNanos() -
                        cached.elapsedRealtimeNanos

                if (age in 0..600_000_000_000L) {
                    return cached.toGeoLocation()
                }
            }

            val signal = CancellationSignal()

            val location =
                try {
                    withTimeoutOrNull(15_000L.milliseconds) {
                        suspendCancellableCoroutine<Location?> { continuation ->
                            continuation.invokeOnCancellation {
                                signal.cancel()
                            }

                            manager.getCurrentLocation(
                                provider,
                                signal,
                                context.mainExecutor,
                            ) { result ->
                                if (continuation.isActive) {
                                    continuation.resume(result)
                                }
                            }
                        }
                    }
                } finally {
                    signal.cancel()
                }

            return location?.toGeoLocation()
        } catch (e: SecurityException) {
            return null
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    private fun Location.toGeoLocation() =
        GeoLocation(
            latitude = latitude,
            longitude = longitude,
        )
}
