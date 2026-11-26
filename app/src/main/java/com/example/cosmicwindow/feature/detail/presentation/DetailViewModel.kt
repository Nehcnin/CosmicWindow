package com.example.cosmicwindow.feature.detail.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.feature.saved.domain.repository.SavedRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod
import com.example.cosmicwindow.feature.today.domain.repository.ApodRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailViewModel(
    private val repository: ApodRepository,
    private val savedRepository: SavedRepository,
) : ViewModel() {
    var apod by mutableStateOf<Apod?>(null)
        private set

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    var isFirstSaved by mutableStateOf(false)
        private set

    private val apiDateFormatter = DateTimeFormatter.ofPattern("yyMMdd")

    fun loadApod(date: String, isLocal: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val formattedDate = LocalDate.parse(date).format(apiDateFormatter)

            try {
                if (!isLocal) {
                    apod = repository.getByDate(formattedDate)
                } else {
                    apod = savedRepository.getByDateSimpleApod(date)
                }
                Log.d("DETAIL", "APOD LOADED")
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load APOD"
                Log.e("DETAIL", "ERROR LOADING APOD(date: $date): ${error.value}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onToggleSaveApod(apod: Apod?) {
        if (apod != null) {
            Log.d("SAVED", "Toggle SAVE APOD")
            isFirstSaved = !isFirstSaved

            saveOrDeleteApod(apod)
        }
    }

    fun saveOrDeleteApod(apod: Apod) {
        viewModelScope.launch {
            try {
                val isSaved = savedRepository.isSaved(apod)

                if (isSaved && !isFirstSaved) {
                    savedRepository.deleteApod(apod.date)
                    Log.d("SAVED", "DELETING APOD")
                } else if (!isSaved && isFirstSaved) {
                    Log.d("SAVED", "SAVING APOD")
                    savedRepository.saveApod(apod)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("SAVED", "Failed to toggle saved APOD")
            }
        }
    }

    fun refreshSavedState() {
        val currApod = apod ?: return

        viewModelScope.launch {
            isFirstSaved = savedRepository.isSaved(currApod)
        }
    }
}
