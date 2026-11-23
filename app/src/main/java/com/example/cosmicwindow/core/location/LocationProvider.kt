package com.example.cosmicwindow.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.CancellationSignal
import android.os.SystemClock
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

data class GeoLocation(
    val latitude: Double,
    val longitude: Double,
)

class LocationProvider(
    private val context: Context,
) {
    suspend fun getLocation(): GeoLocation? {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) {
            Log.d("EPIC_LOCATION", "Coarse permission denied")
            return null
        }

        val manager = context.getSystemService(
            Context.LOCATION_SERVICE,
        ) as LocationManager

        if (!manager.isLocationEnabled) {
            Log.d("EPIC_LOCATION", "Device location is disabled")
            return null
        }

        val providers = listOf(
            LocationManager.NETWORK_PROVIDER,
            LocationManager.GPS_PROVIDER,
        ).filter { provider ->
            runCatching {
                manager.isProviderEnabled(provider)
            }.getOrDefault(false)
        }

        val cachedProviders =
            providers + LocationManager.PASSIVE_PROVIDER

        val cached = cachedProviders
            .distinct()
            .mapNotNull { provider ->
                try {
                    manager.getLastKnownLocation(provider)
                } catch (e: SecurityException) {
                    null
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
            .maxByOrNull { it.elapsedRealtimeNanos }

        val cachedAge = cached?.let {
            (SystemClock.elapsedRealtimeNanos() -
                it.elapsedRealtimeNanos) / 1_000_000L
        }

        if (cached != null &&
            cachedAge != null &&
            cachedAge in 0..600_000L
        ) {
            Log.d(
                "EPIC_LOCATION",
                "Using cached location: ${cached.provider}, " +
                    "age: ${cachedAge / 1000}s",
            )

            return cached.toGeoLocation()
        }

        val signals = mutableListOf<CancellationSignal>()

        val current = try {
            withTimeoutOrNull(15_000L) {
                suspendCancellableCoroutine<Location?> { continuation ->

                    var registered = false

                    providers.forEach { provider ->
                        val signal = CancellationSignal()

                        try {
                            manager.getCurrentLocation(
                                provider,
                                signal,
                                context.mainExecutor,
                            ) { location ->
                                if (location != null &&
                                    continuation.isActive
                                ) {
                                    continuation.resume(location)
                                }
                            }

                            signals.add(signal)
                            registered = true

                        } catch (e: SecurityException) {
                            Log.w(
                                "EPIC_LOCATION",
                                "Provider permission unavailable: $provider",
                            )
                        } catch (e: IllegalArgumentException) {
                            Log.w(
                                "EPIC_LOCATION",
                                "Provider unavailable: $provider",
                            )
                        }
                    }

                    if (!registered && continuation.isActive) {
                        continuation.resume(null)
                    }

                    continuation.invokeOnCancellation {
                        signals.forEach { it.cancel() }
                    }
                }
            }
        } catch (e: CancellationException) {
            throw e
        } finally {
            signals.forEach { it.cancel() }
        }

        if (current != null) {
            Log.d(
                "EPIC_LOCATION",
                "Current location from ${current.provider}, " +
                    "accuracy: ${current.accuracy}m",
            )

            return current.toGeoLocation()
        }

        if (cached != null &&
            cachedAge != null &&
            cachedAge in 0..7_200_000L
        ) {
            Log.d(
                "EPIC_LOCATION",
                "Using older cached location: ${cached.provider}",
            )

            return cached.toGeoLocation()
        }

        Log.d(
            "EPIC_LOCATION",
            "No location available from Android providers",
        )

        return null
    }

    private fun Location.toGeoLocation(): GeoLocation =
        GeoLocation(
            latitude = latitude,
            longitude = longitude,
        )
}
