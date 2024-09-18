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

    override suspend fun getBooksWithSearch(
        search: String,
        languages: List<String>
    ): Flow<ResponseState<List<Book>>> {
        return flow {
            emit(ResponseState.Loading)
            val response = restDataSource.getBooksWithSearch(search)
            if (languages.isEmpty()) {
                emit(ResponseState.Success(response.mapTo { it.toBookList() }))
            } else {
                emit(ResponseState.Success(response.mapTo {
                    it.toBookListWithLanguagesFilter(
                        languages
                    )
                }))
            }
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override suspend fun getBooksWithLanguages(languages: List<String>): Flow<ResponseState<List<Book>>> {
        return flow {
            emit(ResponseState.Loading)
            val response = restDataSource.getBooksWithLanguages(languages)
            emit(ResponseState.Success(response.mapTo { it.toBookList() }))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }
}