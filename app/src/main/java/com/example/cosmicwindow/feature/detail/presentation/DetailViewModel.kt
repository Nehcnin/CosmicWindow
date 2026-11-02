package com.example.cosmicwindow.feature.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.feature.today.domain.ApodRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ApodRepository
): ViewModel() {
    private val _apod = MutableStateFlow<Apod?>(null)
    val apod = _apod.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadApod(date: String){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try{
                _apod.value = repository.getByDate(date)
                Log.d("DETAIL", "APOD LOADED")
            } catch (e: Exception){
                _error.value = e.message ?: "Failed to load APOD"
                Log.e("DETAIL", "ERROR LOADING APOD(date: $date): ${error.value}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}