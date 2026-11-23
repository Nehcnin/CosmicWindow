package com.example.cosmicwindow.feature.earth.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Image
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import com.example.cosmicwindow.core.location.GeoLocation
import com.example.cosmicwindow.core.location.LocationProvider
import com.example.cosmicwindow.feature.earth.domain.model.Epic
import com.example.cosmicwindow.feature.earth.domain.repository.EpicRepository
import com.example.cosmicwindow.feature.earth.presentation.util.detectEarthRadius
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

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

    var locationEnabled by mutableStateOf(false)
        private set

    var  earthRadiusFraction by mutableStateOf<Float?>(null)
        private set

    private val loadedImages = mutableStateMapOf<Int, Image>()

    val allFramesReady: Boolean
        get() = epics.isNotEmpty() && loadedImages.size == epics.size

    fun getLoadedImage(index: Int): Image?{
        return loadedImages[index]
    }

    private var locationJob: Job? = null

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
        if (!allFramesReady || isPlaying) return

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


    private suspend fun loadFrame(index: Int, epic: Epic){
        repeat(3){ attempt ->

            try {
                val result = imageLoader.execute(
                    createImageRequest(epic.imageUrl)
                )

                if (result is SuccessResult) {
                    loadedImages[index] = result.image

                    Log.d(
                        "EPIC_CACHE",
                        "Loaded ${index + 1}/${epics.size}, " +
                            "source=${result.dataSource}"
                    )

                    if (index == 0) {
                        detectRadius(result.image)
                    }

                    return
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.e(
                    "EPIC_CAHCE",
                    "Frame $index attempt ${attempt +1} failed"
                )
            }

            if(attempt < 2){
                delay((500L * attempt+1).milliseconds)
            }
        }
    }

    private suspend fun detectRadius(image: Image){
        val radius = withContext(Dispatchers.Default){
            val bitmap = image.toBitmap()

            val softwareBitmap =
                if(bitmap.config == Bitmap.Config.HARDWARE){
                    bitmap.copy(Bitmap.Config.ARGB_8888, false)
                } else{
                    bitmap
                }

            if(softwareBitmap != null){
                try {
                    detectEarthRadius(softwareBitmap)
                } finally {
                    if(softwareBitmap !==bitmap){
                        softwareBitmap.recycle()
                    }
                }
            } else{
                null
            }
        }

        earthRadiusFraction = radius

        Log.d("EPIC_EARTH", "Earth radius: $radius")
    }

    private fun preloadImages(images: List<Epic>) {
        preloadJob?.cancel()

        preloadJob =
            viewModelScope.launch {
                val semaphore = Semaphore(5)

                coroutineScope {
                    images.mapIndexed { index, epic ->
                        async {
                            semaphore.withPermit {
                                loadFrame(index, epic)
                            }
                        }
                    }.awaitAll()
                }

                Log.d(
                    "EPIC_CACHE",
                    "Preload complete: ${loadedImages.size}/${images.size}"
                )

            }
    }

    fun loadDate(date: String) {
        stopPlayback()
        loadJob?.cancel()
        preloadJob?.cancel()

        loadedImages.clear()
        earthRadiusFraction = null
        currentIndex = 0

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

    fun setLocationEnable(enabled: Boolean){
        locationEnabled = enabled

        locationJob?.cancel()
        locationJob = null

        if(enabled){
            loadLocation()
        } else{
            userLocation = null
        }
    }

    fun loadLocation() {
        locationJob = viewModelScope.launch {

            try {
                val location = locationProvider.getLocation()

                if(locationEnabled){
                    userLocation = location
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.e("EPIC_LOCATION", "Location failed", e)
                userLocation = null
            }

        }
    }
}
