package com.example.cosmicwindow.feature.explore.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmicwindow.feature.explore.domain.ExploreRepository
import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val repository: ExploreRepository
): ViewModel(){

    var isLoading by mutableStateOf(false)
        private set

    var loadJob: Job? = null

    var error by mutableStateOf<String?>(null)

    var items by mutableStateOf<List<ExploreItem>>(emptyList())

    fun loadItems(){
        isLoading = true
        loadJob?.cancel()

        loadJob = viewModelScope.launch {

            try{
                items = repository.getImages()

                items.forEach {
                    Log.d("Explore API","${it.title}\n"+
                        "${it.imageUrl}\n"+
                        "${it.date}\n"+
                        "${it.id}\n"+
                        "${it.center}\n"+
                        "${it.keywords}\n"+
                        "${it.description}\n"
                        )
                }
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                Log.e("Explore API", e.message?:"Error loading items")
            } finally {
                isLoading = false
            }
        }

    }
}
