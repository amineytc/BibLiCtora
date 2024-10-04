package com.amineaytac.biblictora.ui.readinglist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amineaytac.biblictora.R
import com.amineaytac.biblictora.databinding.FragmentReadingListBinding
import com.amineaytc.biblictora.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingListFragment : Fragment(R.layout.fragment_reading_list) {

    private val binding by viewBinding(FragmentReadingListBinding::bind)
    private val viewModel: ReadingListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getReadingBooks()
        observeUi()
    }

    private fun observeUi() = with(binding) {
        viewModel.readingBookScreenUiState.observe(viewLifecycleOwner) {
            when {
                it.isError -> {
                    Log.d("denemeee", it.errorMessage.toString())
                }

                else -> {
                    Log.d("denemeee", it.books.joinToString())
                }
            }
        }
    }
}