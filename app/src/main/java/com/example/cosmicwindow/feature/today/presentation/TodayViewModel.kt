package com.example.cosmicwindow.feature.today.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.feature.today.domain.ApodRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class TodayViewModel(
    private val repository: ApodRepository
) : ViewModel() {
    var apod by mutableStateOf<Apod?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadToday()
    }

    fun loadToday() {

        Log.d("NASA_API", "Starting request")

        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                apod = repository.getToday()

                Log.d("NASA_API", "SUCCESS: ${apod?.title}")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = e.message ?: "Unknown error"

                Log.e("NASA_API", "REQUEST FAILED", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadDate(date: String) {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                apod = repository.getByDate(date)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = e.message ?: "Unable to reach NASA archive"
            } finally {
                isLoading = false
            }
        }
    }
}