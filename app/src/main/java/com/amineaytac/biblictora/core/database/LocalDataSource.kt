package com.amineaytac.biblictora.core.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun addFavoriteItem(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavoriteItem(favoriteEntity: FavoriteEntity)
    suspend fun getFavoriteItems(): Flow<List<FavoriteEntity>>

    fun isItemFavorited(itemId: String): LiveData<Boolean>
}