package com.amineaytac.biblictora.core.database

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun addFavoriteItem(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavoriteItem(favoriteEntity: FavoriteEntity)
    suspend fun getFavoriteItems(): Flow<List<FavoriteEntity>>
}