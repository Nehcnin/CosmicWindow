package com.example.cosmicwindow.feature.earth.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.feature.earth.domain.model.Epic
import com.example.cosmicwindow.feature.earth.domain.repository.EpicRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EarthViewModel(
    private val repository: EpicRepository,
) : ViewModel() {
    var epics by mutableStateOf<List<Epic>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private var loadJob: Job? = null

    fun loadDate(date: String) {
        loadJob?.cancel()

        loadJob =
            viewModelScope.launch {
                isLoading = true
                error = null
                epics = emptyList()

                try {
                    var result = repository.getByDateEnhanced(date)

                    if (result.isEmpty()) {
                        Log.d("EPIC_API", "Requesting Natural version")
                        result = repository.getByDate(date)
                    }

                    if (result.isEmpty()) {
                        Log.d("EPIC_API", "Requesting latest enhanced")
                        result = repository.getLatestEnhanced()
                    }

                    Log.d("EPIC_API", "Requested date: $date")
                    Log.d("EPIC_API", "Image date: ${result.first().captureTime}")
                    Log.d("EPIC_API", "Images count: ${result.size}")

                    epics = result.sortedBy { it.captureTime }
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
}
