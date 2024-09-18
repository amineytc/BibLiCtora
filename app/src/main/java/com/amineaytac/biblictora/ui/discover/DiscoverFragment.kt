package com.amineaytac.biblictora.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.amineaytac.biblictora.R
import com.amineaytac.biblictora.databinding.FragmentDiscoverBinding
import com.amineaytc.biblictora.util.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val binding by viewBinding(FragmentDiscoverBinding::bind)
    private val viewModel: DiscoverViewModel by viewModels()
    private var isChipGroupVisible = false
    private val chips = mutableListOf<LanguageChipBox>()
    private var chipClickStates = Array(12) { false }
    private lateinit var chipAdapter: ChipAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callInitialViewModelFunctions()
        observeUi()
        bindBackDrop()
    }

    private fun bindChipAdapter() = with(binding) {

        chipClickStates = viewModel.getList()
        recyclerView.setHasFixedSize(true)
        chipAdapter =
            ChipAdapter(recyclerView, requireContext(), chips, chipClickStates) { position ->
                viewModel.setChipClickListener(position)
            }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = chipAdapter

        chips.clear()
        val languages = resources.getStringArray(R.array.languages)
        val flagDrawableIds = resources.obtainTypedArray(R.array.flag_drawable_ids)

        languages.forEachIndexed { index, str ->
            val (id, language, abbreviation) = str.split(",")
            chips.add(
                LanguageChipBox(
                    id.toInt(),
                    language,
                    abbreviation,
                    flagDrawableIds.getResourceId(index, -1)
                )
            )
        }
    }

    private fun bindBackDrop() = with(binding) {

        bindChipAdapter()
        bindSearchView()
        isChipGroupVisible = viewModel.getChipGroupVisibility()
        checkChipGroupVisibility()

        icLanguage.setOnClickListener {
            viewModel.setChipGroupVisibility()
            isChipGroupVisible = viewModel.getChipGroupVisibility()
            checkChipGroupVisibility()
        }

        val sheetBehavior = BottomSheetBehavior.from(linearLayout)
        sheetBehavior.isFitToContents = false
        sheetBehavior.isHideable = false
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        btnBackdropFilter.setOnClickListener {
            toggleFilters(sheetBehavior)
        }
    }

    private fun checkChipGroupVisibility() = with(binding) {

        if (!isChipGroupVisible) {
            recyclerView.visibility = View.GONE
            btnShowResults.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            btnShowResults.visibility = View.VISIBLE
        }
    }

    private fun bindSearchView() = with(binding) {

        searchView.setQuery(viewModel.getSearchText(), false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setSearchText(it)
                }
                return true
            }
        })
    }

    private fun toggleFilters(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun callInitialViewModelFunctions() {
        viewModel.getAllBooks()
    }

    private fun observeUi() {
        viewModel.bookScreenUiState.observe(viewLifecycleOwner) {
            when {
                it.isError -> {
                    Log.d("discover_data", it.errorMessage.toString())
                }

                it.isLoading -> {
                    Log.d("discover_data", "loading")
                }

                else -> {
                    Log.d("discover_data", it.books.toString())
                }
            }
        }
    }
}