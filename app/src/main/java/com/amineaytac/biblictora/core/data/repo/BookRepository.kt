package com.amineaytac.biblictora.core.data.repo

import androidx.paging.PagingData
import com.amineaytac.biblictora.core.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getAllBooks(funcKey: String): Flow<PagingData<Book>>
    suspend fun getBooksWithSearch(
        search: String, languages: List<String>, funcKey: String
    ): Flow<PagingData<Book>>

    suspend fun getBooksWithLanguages(
        languages: List<String>, funcKey: String
    ): Flow<PagingData<Book>>

    suspend fun getFavoriteItems(): Flow<List<Book>>
    suspend fun addFavoriteItem(book: Book)
    suspend fun deleteFavoriteItem(book: Book)
}