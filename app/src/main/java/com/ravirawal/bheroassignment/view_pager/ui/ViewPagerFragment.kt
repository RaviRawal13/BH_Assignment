package com.ravirawal.bheroassignment.view_pager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.ravirawal.bheroassignment.R
import com.ravirawal.bheroassignment.beer_list_one.ui.BeerListOneFragment
import com.ravirawal.bheroassignment.beer_list_two.ui.BeerListTwoFragment
import com.ravirawal.bheroassignment.databinding.FragmentViewPagerBinding
import com.ravirawal.bheroassignment.network.data_source.BeerSources
import com.ravirawal.bheroassignment.retrofit.ServiceHelper
import com.ravirawal.bheroassignment.shared_vm.BeerListViewModel
import com.ravirawal.bheroassignment.shared_vm.BeerListViewModelFactory
import com.ravirawal.bheroassignment.view_pager.adapter.ViewPagerAdapter

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private lateinit var binding: FragmentViewPagerBinding

    private lateinit var viewModel: BeerListViewModel

    private val fragmentList = arrayListOf(
        BeerListOneFragment(),
        BeerListTwoFragment(),
    )

    private val beerListViewModelFactory by lazy {
        BeerListViewModelFactory(
            BeerSources(
                ServiceHelper.getAPIHelper()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentViewPagerBinding.bind(view)
        activity?.let {
            viewModel =
                ViewModelProvider(
                    it,
                    beerListViewModelFactory
                ).get(BeerListViewModel::class.java)
        }
        binding.tabLayoutViewPager.getTabAt(viewModel.selectedTab)?.select()
        binding.tabLayoutViewPager.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.selectedTab = tab?.position ?: 0

                binding.viewPager.currentItem = viewModel.selectedTab
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
    }

}

