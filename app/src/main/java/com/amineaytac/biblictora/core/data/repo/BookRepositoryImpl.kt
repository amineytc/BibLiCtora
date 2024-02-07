package com.amineaytac.biblictora.core.data.repo

import com.amineaytac.biblictora.core.common.ResponseState
import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.network.source.rest.RestDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val restDataSource: RestDataSource) :
    BookRepository {
    override suspend fun getAllBooks(): Flow<ResponseState<List<Book>>> {
        return flow {
            emit(ResponseState.Loading)
            val response = restDataSource.getAllBooks()
            emit(ResponseState.Success(response.mapTo { it.toBookList() }))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }
}