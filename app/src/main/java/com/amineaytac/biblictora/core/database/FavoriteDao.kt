package com.amineaytac.biblictora.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteItem(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteItem(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun getFavoriteItems(): Flow<List<FavoriteEntity>>
}