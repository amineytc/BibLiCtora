package com.amineaytac.biblictora.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.core.data.repo.BookRepository
import com.amineaytac.biblictora.core.domain.AddFavoriteItemUseCase
import com.amineaytac.biblictora.core.domain.DeleteFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val addFavoriteItemUseCase: AddFavoriteItemUseCase,
    private val deleteFavoriteItemUseCase: DeleteFavoriteItemUseCase,
    private val bookRepository: BookRepository
) : ViewModel() {
    fun addFavoriteItem(book: Book) {
        viewModelScope.launch {
            addFavoriteItemUseCase(book)
        }
    }
    fun deleteFavoriteItem(book: Book) {
        viewModelScope.launch {
            deleteFavoriteItemUseCase(book)
        }
    }
    fun isItemFavorited(itemId: String): LiveData<Boolean> {
        return bookRepository.isItemFavorited(itemId)
    }
}