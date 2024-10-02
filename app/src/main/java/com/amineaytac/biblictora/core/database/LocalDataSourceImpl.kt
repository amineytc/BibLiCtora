package com.amineaytac.biblictora.core.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : LocalDataSource {

    override suspend fun addFavoriteItem(favoriteEntity: FavoriteEntity) {
        favoriteDao.addFavoriteItem((favoriteEntity))
    }

    override suspend fun deleteFavoriteItem(favoriteEntity: FavoriteEntity) {
        favoriteDao.deleteFavoriteItem(favoriteEntity)
    }

    override suspend fun getFavoriteItems(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getFavoriteItems()
    }
}