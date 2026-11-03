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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TodayViewModel(
    private val repository: ApodRepository
) : ViewModel() {

    var date by mutableStateOf(LocalDate.now())
        private set
    var apod by mutableStateOf<Apod?>(null)
        private set

    var apods by mutableStateOf<List<Apod>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private var loadJob: Job? = null



    private val apiDateFormatter = DateTimeFormatter.ofPattern("yyMMdd")

    init {
        loadDate()
    }

    fun updateDate(newDate: LocalDate){
        if(newDate == date) return

        date = newDate
        loadDate()
    }

    fun loadToday() {
        updateDate(LocalDate.now())
    }

    fun loadDate() {
        loadJob?.cancel()

        loadJob = viewModelScope.launch {
            isLoading = true
            error = null
            apod = null
            apods = emptyList()

            try{
                val startDate = date.minusDays(7)
                val endDate = date

                val result = repository.getRange(
                    startDate.format(apiDateFormatter),
                    endDate.format(apiDateFormatter)
                )

                apods = result.sortedByDescending { it.date }.take(8)

                apod = apods.firstOrNull{
                    it.date == date.toString()
                }

                Log.d("NASA_API", "SUCCESS: ${apod?.title}")
                Log.d("NASA_API", "VIDEO URL: ${apod?.videoUrl}")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = e.message ?: "Unable to reach NASA archive"

                Log.e("NASA_API", "REQUEST FAILED", e)
            } finally {
                isLoading = false
            }
        }
    }
}