package com.amineaytac.biblictora.core.domain

import com.amineaytac.biblictora.core.common.ResponseState
import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.data.repo.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksWithSearchUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend operator fun invoke(
        search: String,
        languages: List<String>
    ): Flow<ResponseState<List<Book>>> {
        return bookRepository.getBooksWithSearch(search, languages)
    }
}