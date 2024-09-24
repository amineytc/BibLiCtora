package com.amineaytac.biblictora.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amineaytac.biblictora.core.domain.GetAllBooksUseCase
import com.amineaytac.biblictora.core.domain.GetBooksWithLanguagesUseCase
import com.amineaytac.biblictora.core.domain.GetBooksWithSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getBooksWithSearchUseCase: GetBooksWithSearchUseCase,
    private val getBooksWithLanguagesUseCase: GetBooksWithLanguagesUseCase
) : ViewModel() {

    private val _bookScreenUiState = MutableLiveData<BookListScreenUiState>()
    val bookScreenUiState: LiveData<BookListScreenUiState> get() = _bookScreenUiState

    private var chipClickStates = Array(12) { false }
    private var searchText = ""
    private var isChipGroupVisible = false

    fun getAllBooks() {
        viewModelScope.launch {
            getAllBooksUseCase().catch {
                _bookScreenUiState.postValue(
                    BookListScreenUiState(
                        isError = true, errorMessage = it.message
                    )
                )
            }.collect { data ->
                _bookScreenUiState.postValue(BookListScreenUiState(data))
            }
        }
    }

    fun getBooksWithSearch(search: String, languages: List<String>) {
        viewModelScope.launch {
            getBooksWithSearchUseCase(search, languages).catch {
                _bookScreenUiState.postValue(
                    BookListScreenUiState(
                        isError = true, errorMessage = it.message
                    )
                )
            }.collect { data ->
                _bookScreenUiState.postValue(BookListScreenUiState(data))
            }
        }
    }

    fun getBooksWithLanguages(languages: List<String>) {
        viewModelScope.launch {
            getBooksWithLanguagesUseCase(languages).catch {
                _bookScreenUiState.postValue(
                    BookListScreenUiState(
                        isError = true, errorMessage = it.message
                    )
                )
            }.collect { data ->
                _bookScreenUiState.postValue(BookListScreenUiState(data))
            }
        }
    }

    fun setChipClickListener(position: Int) {
        chipClickStates[position] = !chipClickStates[position]
    }

    fun getChipClickStates(): Array<Boolean> {
        return chipClickStates
    }

    fun setSearchText(str: String) {
        searchText = str
    }

    fun getSearchText(): String {
        return searchText
    }

    fun setChipGroupVisibility() {
        isChipGroupVisible = !isChipGroupVisible
    }

    fun getChipGroupVisibility(): Boolean {
        return isChipGroupVisible
    }
}