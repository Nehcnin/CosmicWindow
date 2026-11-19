package com.example.cosmicwindow.core.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.cosmicwindow.feature.saved.data.SavedApodDao
import com.example.cosmicwindow.feature.saved.data.SavedApodEntity

@Database(
    entities = [SavedApodEntity::class],
    version = 1
)
abstract class CosmicDatabase : RoomDatabase(){
    abstract fun savedApodDao(): SavedApodDao
}
