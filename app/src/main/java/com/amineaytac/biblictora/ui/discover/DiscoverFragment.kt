package com.amineaytac.biblictora.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amineaytac.biblictora.R
import com.amineaytac.biblictora.databinding.FragmentDiscoverBinding
import com.amineaytc.biblictora.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val binding by viewBinding(FragmentDiscoverBinding::bind)

    private val viewModel :  DiscoverViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callInitialViewModelFunctions()
        observeUi()

    }

    private fun callInitialViewModelFunctions() {
        viewModel.getAllBooks()
    }

    private fun observeUi() {
        viewModel.bookScreenUiState.observe(viewLifecycleOwner) {
            when {
                it.isError -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG)
                    Log.d("discoverdata", "error")
                }

                it.isLoading -> {
                    Log.d("discoverdata", "loading")
                }

                else -> {
                    Log.d("discoverdata", it.books.toString())
                }
            }
        }
    }
}
