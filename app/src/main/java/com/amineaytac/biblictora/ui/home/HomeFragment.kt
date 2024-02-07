package com.amineaytac.biblictora.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.amineaytac.biblictora.R
import com.amineaytac.biblictora.databinding.FragmentHomeBinding
import com.amineaytac.biblictora.ui.discover.DiscoverFragment
import com.amineaytac.biblictora.ui.readinglist.ReadingListFragment
import com.amineaytc.biblictora.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindTabLayout()
    }

    private fun bindTabLayout() {
        val fragmentList = arrayListOf(
            DiscoverFragment(),
            ReadingListFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        val tabLayoutMediator = adapter.createTabLayoutMediator(binding)
        binding.viewPager.adapter = adapter
        tabLayoutMediator.attach()
    }
}
