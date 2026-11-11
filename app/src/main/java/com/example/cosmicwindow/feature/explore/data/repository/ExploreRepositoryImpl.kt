package com.example.cosmicwindow.feature.explore.data.repository

import com.example.cosmicwindow.core.network.ExploreApiService
import com.example.cosmicwindow.feature.explore.data.dto.toDomain
import com.example.cosmicwindow.feature.explore.domain.ExploreRepository
import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem

class ExploreRepositoryImpl (
    private val api: ExploreApiService
) : ExploreRepository{
    override suspend fun getImages(
        query: String?,
        page: Int,
        pageSize: Int
    ): List<ExploreItem> {
        return api.getImages(
            query = query,
            pageSize = pageSize,
            page = page
        ).toDomain()
    }

}
