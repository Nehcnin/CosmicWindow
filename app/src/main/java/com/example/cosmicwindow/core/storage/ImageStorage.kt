package com.example.cosmicwindow.core.storage

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class ImageStorage(
    private val context: Context,
) {
    private val directory =
        File(context.filesDir, "apods").apply {
            mkdir()
        }

    suspend fun saveImage(imageUrl: String, date: String): String =
        withContext(Dispatchers.IO) {
            val connection =
                URL(imageUrl).openConnection() as HttpURLConnection

            connection.connectTimeout = 15_000
            connection.readTimeout = 30_000

            val tempFile =
                File.createTempFile("apod_", ".tmp", directory)

            try {
                connection.connect()

                check(connection.responseCode in 200..209) {
                    "Image download failed: ${connection.responseCode}"
                }

                val extension =
                    when (
                        connection.contentType
                            ?.substringBefore(";")
                            ?.lowercase()
                    ) {
                        "image/jpeg" -> "jpg"
                        "image/png" -> "png"
                        "image/webp" -> "webp"
                        else -> error("Unsupported image format")
                    }

                val file = File(directory, "$date.$extension")

                if (file.exists()) {
                    return@withContext "apods/${file.name}"
                }

                connection.inputStream.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                check(tempFile.length() > 0L) {
                    "Downloaded image is empty"
                }

                check(tempFile.renameTo(file)) {
                    "Could not save image"
                }

                "apods/${file.name}"
            } finally {
                tempFile.delete()
                connection.disconnect()
            }
        }

    fun getImageFile(path: String): File = File(context.filesDir, path)

    suspend fun deleteImage(path: String) =
        withContext(Dispatchers.IO) {
            val file = getImageFile(path)

            if (file.exists()) {
                check(file.delete()) {
                    "Could not delete image"
                }
            }
        }

    suspend fun getTotalSizesMB(): Double =
        withContext(Dispatchers.IO) {
            val totalBytes =
                directory
                    .listFiles()
                    ?.filter { it.isFile }
                    ?.sumOf { it.length() }
                    ?: 0L

            totalBytes / (1024.0 * 1024.0)
        }
}
