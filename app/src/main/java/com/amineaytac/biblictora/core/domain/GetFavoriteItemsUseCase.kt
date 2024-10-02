package com.amineaytac.biblictora.core.domain

import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.data.repo.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteItemsUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend operator fun invoke(): Flow<List<Book>> {
        return bookRepository.getFavoriteItems()
    }
}