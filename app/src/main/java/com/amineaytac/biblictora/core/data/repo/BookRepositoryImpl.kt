package com.amineaytac.biblictora.core.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.database.LocalDataSource
import com.amineaytac.biblictora.core.network.source.paging.PagingSource
import com.amineaytac.biblictora.core.network.source.rest.RestDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val restDataSource: RestDataSource,
    private val localDataSource: LocalDataSource
) :
    BookRepository {
    override suspend fun getAllBooks(funcKey: String): Flow<PagingData<Book>> {
        val pagingSource = PagingSource(restDataSource, funcKey)
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource }).flow
    }

    override suspend fun getBooksWithSearch(
        search: String, languages: List<String>, funcKey: String
    ): Flow<PagingData<Book>> {
        val pagingSource = PagingSource(restDataSource, funcKey, search, languages)
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource }).flow
    }

    override suspend fun getBooksWithLanguages(
        languages: List<String>, funcKey: String
    ): Flow<PagingData<Book>> {
        val pagingSource = PagingSource(restDataSource, funcKey, languages = languages)
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource }).flow
    }

    override suspend fun getFavoriteItems(): Flow<List<Book>> {
        return localDataSource.getFavoriteItems()
            .map { it.map { favoriteEntity -> favoriteEntity.toBook() } }
    }

    override suspend fun addFavoriteItem(book: Book) {
        localDataSource.addFavoriteItem(book.toFavoriteItemEntity())
    }

    override suspend fun deleteFavoriteItem(book: Book) {
        localDataSource.deleteFavoriteItem(book.toFavoriteItemEntity())
    }

    override fun isItemFavorited(itemId: String): LiveData<Boolean> {
        return localDataSource.isItemFavorited(itemId)
    }
}