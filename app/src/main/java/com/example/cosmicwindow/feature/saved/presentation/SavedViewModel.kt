package com.example.cosmicwindow.feature.saved.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.core.storage.ImageStorage
import com.example.cosmicwindow.feature.saved.domain.model.SavedApod
import com.example.cosmicwindow.feature.saved.domain.repository.SavedRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedViewModel(
    private val savedRepository: SavedRepository,
    private val imageStorage: ImageStorage
): ViewModel(){

    private val _storageSizeMB = MutableStateFlow(0.0)
    val storageSizeMB = _storageSizeMB.asStateFlow()

    fun refreshStorageSize(){
        viewModelScope.launch {
            _storageSizeMB.value = imageStorage.getTotalSizesMB()
        }
    }

    val savedApods = savedRepository.getSavedApods()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun deleteSavedApod(apod: SavedApod){
        viewModelScope.launch {
            savedRepository.deleteApod(apod.date)

            refreshStorageSize()
        }
    }

}
