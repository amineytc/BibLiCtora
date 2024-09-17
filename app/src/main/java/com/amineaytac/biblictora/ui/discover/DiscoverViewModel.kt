package com.amineaytac.biblictora.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amineaytac.biblictora.core.common.ResponseState
import com.amineaytac.biblictora.core.domain.GetAllBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val getAllBooksUseCase: GetAllBooksUseCase) :
    ViewModel() {

    private val _bookScreenUiState = MutableLiveData(BookListScreenUiState.initial())
    val bookScreenUiState: LiveData<BookListScreenUiState> get() = _bookScreenUiState

    private var chipClickStates = Array(12) { false }
    private var searchText = ""
    private var isChipGroupVisible = false

    fun getAllBooks() {
        viewModelScope.launch {
            getAllBooksUseCase().collect { responseState ->
                when (responseState) {
                    is ResponseState.Error -> {
                        _bookScreenUiState.postValue(
                            BookListScreenUiState(
                                isError = true,
                                errorMessage = responseState.message
                            )
                        )
                    }

                    is ResponseState.Loading -> {
                        _bookScreenUiState.postValue(BookListScreenUiState(isLoading = true))
                    }

                    is ResponseState.Success -> {
                        _bookScreenUiState.postValue(BookListScreenUiState(responseState.data))
                    }
                }
            }
        }
    }

    fun setChipClickListener(position: Int) {
        chipClickStates[position] = !chipClickStates[position]
    }

    fun getList(): Array<Boolean> {
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