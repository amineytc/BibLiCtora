package com.amineaytac.biblictora.ui.discover

import com.amineaytac.biblictora.core.data.model.Book

data class BookListScreenUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = ""
) {
    companion object {
        fun initial() = BookListScreenUiState(isLoading = true)
    }
}