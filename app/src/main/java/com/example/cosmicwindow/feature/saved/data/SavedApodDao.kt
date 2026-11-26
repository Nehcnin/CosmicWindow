package com.example.cosmicwindow.feature.saved.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedApodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(apod: SavedApodEntity): Long

    @Query("SELECT * FROM saved_apods WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): SavedApodEntity?

    @Query("SELECT * FROM saved_apods")
    fun getAll(): Flow<List<SavedApodEntity>>

    @Query("DELETE FROM saved_apods WHERE date = :date")
    suspend fun delete(date: String)
}
