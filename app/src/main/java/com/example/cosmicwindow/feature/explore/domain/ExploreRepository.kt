package com.example.cosmicwindow.feature.explore.domain

import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem

interface ExploreRepository {
    suspend fun getImages(
        query: String? = null,
        page: Int = 1,
        keywords: String? = null,
        pageSize: Int = 20,
    ): List<ExploreItem>
}
