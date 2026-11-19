package com.example.cosmicwindow.feature.saved.domain.repository

import com.example.cosmicwindow.feature.saved.domain.model.SavedApod
import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.coroutines.flow.Flow

interface SavedRepository {
    suspend fun saveApod(apod: Apod)

    fun getSavedApods(): Flow<List<SavedApod>>

    suspend fun isSaved(apod: Apod): Boolean

    suspend fun deleteApod(date: String)

    suspend fun getByDate(date: String): SavedApod?

    suspend fun getByDateSimpleApod(date: String): Apod?
}
