package com.example.cosmicwindow.feature.saved.data.repository

import com.example.cosmicwindow.core.storage.ImageStorage
import com.example.cosmicwindow.feature.saved.data.SavedApodDao
import com.example.cosmicwindow.feature.saved.data.SavedApodEntity
import com.example.cosmicwindow.feature.saved.domain.model.SavedApod
import com.example.cosmicwindow.feature.saved.domain.repository.SavedRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate

class SavedRepositoryImpl(
    private val dao: SavedApodDao,
    private val imageStorage: ImageStorage,
) : SavedRepository {
    private val mutex = Mutex()

    override suspend fun saveApod(apod: Apod) =
        mutex.withLock {
            if (dao.getByDate(apod.date) != null) {
                return@withLock
            }

            val path = imageStorage.saveImage(imageUrl = apod.imgUrl ?: "", date = apod.date)

            try {
                dao.insert(
                    apod =
                        SavedApodEntity(
                            date = apod.date,
                            title = apod.title,
                            explanation = apod.explanation,
                            imageUrl = apod.imgUrl ?: "",
                            localImagePath = path,
                            SavedDate = LocalDate.now().toString(),
                            alt = apod.alt ?: "",
                            credit = apod.credit ?: "NASA",
                            copyright = apod.copyright ?: "NASA",
                            articleUrl = apod.articleUrl ?: "",
                            videoUrl = apod.videoUrl ?: "",
                        ),
                )
            } catch (e: Exception) {
                try {
                    imageStorage.deleteImage(path)
                } catch (cleanupError: Exception) {
                    e.addSuppressed(cleanupError)
                }
                throw e
            }
        }

    override suspend fun getByDate(date: String): SavedApod? {
        val entity = dao.getByDate(date) ?: return null

        return SavedApod(
            date = entity.date,
            title = entity.title,
            explanation = entity.explanation,
            localImagePath = entity.localImagePath,
            saveDate = entity.SavedDate,
            alt = entity.alt,
            credit = entity.credit,
            copyright = entity.copyright,
            articleUrl = entity.articleUrl,
            videoUrl = entity.videoUrl,
        )
    }

    override suspend fun getByDateSimpleApod(date: String): Apod? {
        val entity = dao.getByDate(date) ?: return null

        return Apod(
            date = entity.date,
            title = entity.title,
            explanation = entity.explanation,
            imgUrl = entity.localImagePath,
            alt = entity.alt,
            credit = entity.credit,
            copyright = entity.copyright,
            articleUrl = entity.articleUrl,
            mediaType = "local",
            videoUrl = if (entity.videoUrl.isBlank()) null else entity.videoUrl,
        )
    }

    override fun getSavedApods(): Flow<List<SavedApod>> =
        dao.getAll().map { entities ->
            entities.map { entity ->
                SavedApod(
                    date = entity.date,
                    title = entity.title,
                    explanation = entity.explanation,
                    localImagePath = entity.localImagePath,
                    saveDate = entity.SavedDate,
                    alt = entity.alt,
                    credit = entity.credit,
                    copyright = entity.copyright,
                    articleUrl = entity.articleUrl,
                    videoUrl = entity.videoUrl,
                )
            }
        }

    override suspend fun isSaved(apod: Apod): Boolean {
        if (dao.getByDate(apod.date) != null) {
            return true
        }
        return false
    }

    override suspend fun deleteApod(date: String) =
        mutex.withLock {
            val apod = dao.getByDate(date) ?: return@withLock

            dao.delete(date)

            imageStorage.deleteImage(apod.localImagePath)
        }
}
