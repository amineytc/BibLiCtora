package com.amineaytac.biblictora.core.domain

import com.amineaytac.biblictora.core.data.repo.BookRepository
import javax.inject.Inject

class UpdatePercentageUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend operator fun invoke(bookId: Int, readingPercentage: Int) {
        return bookRepository.updatePercentage(bookId, readingPercentage)
    }
}