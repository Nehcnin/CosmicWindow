package com.example.cosmicwindow.feature.explore.data.dto

import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreResponse(
    val collection: ExploreCollection
)

@Serializable
data class ExploreCollection(
    val items: List<ExploreItemDto>
)

@Serializable
data class ExploreItemDto(
    val data: List<ExampleDataDto>,
    val links: List<ExampleLinkDto> = emptyList()
)

@Serializable
data class ExampleDataDto(
    @SerialName("nasa_id")
    val id : String,
    val center: String = "",
    @SerialName("date_created")
    val date: String = "",
    val description: String = "",
    val keywords: List<String> = emptyList(),
    @SerialName("media_type")
    val mediaType: String = "image",
    val title: String = ""
)

@Serializable
data class ExampleLinkDto(
    val href: String,
)

fun ExploreResponse.toDomain(): List<ExploreItem>{

    return collection.items.mapNotNull { item ->
        val data = item.data.firstOrNull()?: return@mapNotNull null

        ExploreItem(
            center = data.center,
            date = data.date,
            description = data.description,
            keywords = data.keywords,
            id = data.id,
            title = data.title,
            imageUrl =
                item.links.firstOrNull{ it.href.contains("orig.jpg") }?.href
                    ?:item.links.firstOrNull{ it.href.contains("orig.png") }?.href
                    ?:item.links.firstOrNull{it.href.contains("large.jpg")}?.href
                    ?:item.links.firstOrNull{it.href.contains("large.png")}?.href
                    ?:item.links.firstOrNull()?.href
                    .orEmpty()
        )
    }
}
