package com.example.cosmicwindow.feature.explore.domain

import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem

interface ExploreRepository {
    suspend fun getImages(
        query: String? =null,
        page: Int = 1,
        pageSize: Int = 20
    ): List<ExploreItem>
}
