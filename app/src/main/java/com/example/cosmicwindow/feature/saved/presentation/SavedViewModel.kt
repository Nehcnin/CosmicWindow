package com.example.cosmicwindow.feature.saved.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.core.storage.ImageStorage
import com.example.cosmicwindow.feature.saved.domain.model.SavedApod
import com.example.cosmicwindow.feature.saved.domain.repository.SavedRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private val _pendingDeletes = MutableStateFlow<Set<String>>(emptySet())
    val pendingDeletes = _pendingDeletes.asStateFlow()

    private val deleteJobs = mutableMapOf<String, Job>()

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

        if(apod.date in pendingDeletes.value) return

        _pendingDeletes.value += apod.date

        deleteJobs[apod.date] = viewModelScope.launch {
            delay(5000)

            try {
                savedRepository.deleteApod(apod.date)
                refreshStorageSize()
            } finally {
                _pendingDeletes.value -= apod.date
                deleteJobs.remove(apod.date)
            }
        }

    }

    fun undoDelete(date: String){
        deleteJobs.remove(date)?.cancel()
        _pendingDeletes.value -= date
    }

}
