package com.amineaytac.biblictora.core.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.network.source.paging.PagingSource
import com.amineaytac.biblictora.core.network.source.rest.RestDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val restDataSource: RestDataSource) :
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
}