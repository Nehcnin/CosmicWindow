package com.example.cosmicwindow.feature.today.data.dto

import com.example.cosmicwindow.feature.today.domain.model.Apod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApodDto(
    val date: String,
    val title: String,
    val explanation: String,

    @SerialName("media_type")
    val mediaType: String,

    @SerialName("post_id")
    val postId: Long? = null,

    val permalink: String? = null,
    val url: String? = null,
    val hdurl: String? = null,
    val credit: String? = null,
    val copyright: String? = null,
    val alt: String? = null
)

fun ApodDto.toDomain(): Apod = Apod(
    date = date,
    title = title,
    explanation = explanation,
    mediaType = mediaType,
    imgUrl = hdurl,
    articleUrl = permalink ?: url,
    copyright = copyright,
    credit = credit,
    alt = alt
)