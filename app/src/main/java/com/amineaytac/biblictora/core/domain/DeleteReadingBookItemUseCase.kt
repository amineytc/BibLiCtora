package com.amineaytac.biblictora.core.domain

import com.amineaytac.biblictora.core.data.model.ReadingBook
import com.amineaytac.biblictora.core.data.repo.BookRepository
import javax.inject.Inject

class DeleteReadingBookItemUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend operator fun invoke(readingBook: ReadingBook) {
        return bookRepository.deleteReadingBookItem(readingBook)
    }
}