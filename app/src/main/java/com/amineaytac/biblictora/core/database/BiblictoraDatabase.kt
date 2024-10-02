package com.amineaytac.biblictora.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class BiblictoraDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}