package com.example.cosmicwindow.feature.today.data.dto

import androidx.core.text.HtmlCompat
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

    @SerialName("basic_html")
    val basicHtml: String? = null,

    val permalink: String? = null,
    val url: String? = null,
    val hdurl: String? = null,
    val credit: String? = null,
    val copyright: String? = null,
    val alt: String? = null
)

private fun decodeHtml(html: String?): String? {
    return html?.let {
        HtmlCompat.fromHtml(
            it,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        ).toString().trim()
    }
}

fun ApodDto.toDomain(): Apod = Apod(
    date = date,
    title = decodeHtml(title).orEmpty(),
    explanation = decodeHtml(explanation).orEmpty(),
    mediaType = mediaType,
    imgUrl = hdurl,
    articleUrl = permalink ?: url,
    copyright = decodeHtml(copyright),
    credit = decodeHtml(credit),
    alt = decodeHtml(alt),
    videoUrl = if (mediaType == "video") {
        extractVideoUrl(basicHtml)
    } else {
        null
    }
)

private fun extractVideoUrl(html: String?): String? {
    if (html.isNullOrBlank()) return null

    return Regex(
        """<source[^>]*src=["']([^"']+)["']""",
        RegexOption.IGNORE_CASE
    ).find(html)?.groupValues?.get(1)
}