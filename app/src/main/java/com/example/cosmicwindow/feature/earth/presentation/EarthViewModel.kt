package com.example.cosmicwindow.feature.earth.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import coil3.request.ImageRequest
import com.example.cosmicwindow.core.location.GeoLocation
import com.example.cosmicwindow.core.location.LocationProvider
import com.example.cosmicwindow.feature.earth.domain.model.Epic
import com.example.cosmicwindow.feature.earth.domain.repository.EpicRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class EarthViewModel(
    private val repository: EpicRepository,
    private val imageLoader: ImageLoader,
    private val createImageRequest: (String) -> ImageRequest,
    private val locationProvider: LocationProvider
) : ViewModel() {
    var currentIndex by mutableIntStateOf(0)
        private set
    var isPlaying by mutableStateOf(false)
        private set
    var epics by mutableStateOf<List<Epic>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var userLocation by mutableStateOf<GeoLocation?>(null)
        private set

    private var loadJob: Job? = null
    private var playbackJob: Job? = null
    private var preloadJob: Job? = null

    fun togglePlayback() {
        if (!isPlaying) {
            startPlayback()
        } else {
            stopPlayback()
        }
    }

    private fun startPlayback() {
        if (epics.isEmpty() || isPlaying) return

        isPlaying = true

        playbackJob =
            viewModelScope.launch {
                while (true) {
                    delay(300)
                    nextFrame()
                }
            }
    }

    private fun stopPlayback() {
        playbackJob?.cancel()
        playbackJob = null
        isPlaying = false
    }

    fun nextFrame() {
        if (epics.isEmpty()) return

        currentIndex = ++currentIndex % epics.size
    }

    fun previousFrame() {
        if (epics.isEmpty()) return

        currentIndex = (--currentIndex + epics.size) % epics.size
    }

    fun setFrame(index: Int) {
        if (index in epics.indices) {
            currentIndex = index
        }
    }

    fun imageRequest(url: String): ImageRequest =
        createImageRequest(url)

    private fun preloadImages(images: List<Epic>) {
        preloadJob?.cancel()

        preloadJob =
            viewModelScope.launch {
                val semaphore = Semaphore(5)

                images.forEach { epic ->
                    launch {
                        semaphore.withPermit {
                            try {
                                val result = imageLoader.execute(
                                    createImageRequest(epic.imageUrl),
                                )

                                Log.d(
                                    "EPIC_CACHE",
                                    "Source " +
                                        "Cache: ${imageLoader.memoryCache?.size} / " +
                                        "${imageLoader.memoryCache?.maxSize}",
                                )

                            } catch (e: CancellationException) {
                                throw e
                            } catch (e: Exception) {
                                Log.e("EPIC_CACHE", "Preload failed", e)
                            }
                        }
                    }
                }
            }
    }

    fun loadDate(date: String) {
        stopPlayback()
        preloadJob?.cancel()
        currentIndex = 0
        loadJob?.cancel()

        loadJob =
            viewModelScope.launch {
                isLoading = true
                error = null
                epics = emptyList()

                try {
                    var result = repository.getByDateEnhanced(date)

                    if (result.isEmpty()) {
                        Log.d("EPIC_API", "Requesting latest enhanced")
                        result = repository.getLatestEnhanced()
                    }

                    Log.d("EPIC_API", "Requested date: $date")
                    Log.d("EPIC_API", "Image date: ${result.firstOrNull()?.captureTime}")
                    Log.d("EPIC_API", "Images count: ${result.size}")

                    epics = result.sortedBy { it.captureTime }

                    preloadImages(epics)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    error = e.message ?: "Unable to reach NASA EPIC"

                    Log.e("EPIC_API", "REQUEST FAILED", e)
                } finally {
                    isLoading = false
                }
            }
    }

    fun loadLocation() {
        viewModelScope.launch {
            userLocation = locationProvider.getLocation()

            Log.d(
                "EPIC_LOCATION",
                "Location result: $userLocation"
            )

        }
    }
}
