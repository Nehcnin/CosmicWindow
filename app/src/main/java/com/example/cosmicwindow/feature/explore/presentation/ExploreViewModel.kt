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
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var query by mutableStateOf("")
        private set

    var currentQuery = ""
        private set

    var currentPage = 1

    var pageSize = 12

    var isLoadingMore by mutableStateOf(false)
        private set

    var hasMorePages = true

    var loadJob: Job? = null

    var error by mutableStateOf<String?>(null)

    var items by mutableStateOf<List<ExploreItem>>(emptyList())

    fun onQueryChange(value: String){
        query = value
    }

    fun onSearch(){
        currentQuery = query
        loadItems()
    }

    fun loadNextPage() {
        if (hasMorePages&&!isLoadingMore) {
            isLoadingMore = true

            loadJob = viewModelScope.launch {

                try {
                    val result = repository.getImages(page = currentPage, pageSize = pageSize,query = query)

                    result.forEach {
                        Log.d(
                            "Explore API", "${it.title}\n" +
                                "${it.imageUrl}\n" +
                                "${it.date}\n" +
                                "${it.id}\n" +
                                "${it.center}\n" +
                                "${it.keywords}\n" +
                                "${it.description}\n"
                        )
                    }

                    if(result.size < pageSize){
                        hasMorePages = false
                    }

                    currentPage++

                    items = items + result
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    Log.e("Explore API", e.message ?: "Error loading items")
                } finally {
                    isLoading = false
                    isLoadingMore = false
                }
            }
        }
    }

    fun loadItems() {
        isLoading = true
        currentPage = 1
        hasMorePages = true
        items = emptyList()
        error = null
        loadNextPage()
    }
}
