package com.amineaytac.biblictora.core.data.repo

import com.amineaytac.biblictora.core.common.ResponseState
import com.amineaytac.biblictora.core.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getAllBooks(): Flow<ResponseState<List<Book>>>
}