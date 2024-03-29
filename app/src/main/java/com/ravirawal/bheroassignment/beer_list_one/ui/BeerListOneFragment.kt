package com.ravirawal.bheroassignment.beer_list_one.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ravirawal.bheroassignment.R
import com.ravirawal.bheroassignment.beer_list_one.adapter.BeerListAdapter
import com.ravirawal.bheroassignment.databinding.FragmentRecyclerviewBinding
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.network.data_source.BeerSources
import com.ravirawal.bheroassignment.retrofit.ServiceHelper
import com.ravirawal.bheroassignment.shared_vm.BeerListViewModel
import com.ravirawal.bheroassignment.shared_vm.BeerListViewModelFactory
import com.ravirawal.bheroassignment.utils.BUNDLE_BEER_ITEM
import com.ravirawal.bheroassignment.view_pager.ui.ViewPagerFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BeerListOneFragment : Fragment(R.layout.fragment_recyclerview) {

    private lateinit var viewModel: BeerListViewModel
    private lateinit var binding: FragmentRecyclerviewBinding

    private val beerListAdapter =
        BeerListAdapter { beerUi: BeerUi, i: Int ->
            onRecyclerItemClick(
                beerUi,
                i
            )
        }

    private val beerListViewModelFactory by lazy {
        BeerListViewModelFactory(
            BeerSources(
                ServiceHelper.getAPIHelper()
            )
        )
    }

    private fun onRecyclerItemClick(beerUi: BeerUi, i: Int) {
        when (i) {
            R.id.constraint_layout_parent_tab_one -> {
                val action =
                    ViewPagerFragmentDirections.actionViewPagerFragmentToBeerDetailFragment(beerUi)
                parentFragment?.findNavController()?.navigate(action)
            }
            R.id.checkbox_selected_tab_one -> {
                viewModel.onNewEditRequest(beerUi)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        collectLatestData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecyclerviewBinding.bind(view)

        activity?.let {
            viewModel =
                ViewModelProvider(
                    it,
                    beerListViewModelFactory
                ).get(BeerListViewModel::class.java)
        }

        binding.recyclerViewBeerListFragment.adapter = beerListAdapter

        collectLatestData()
    }

    private fun collectLatestData() {
        lifecycleScope.launch {
            viewModel.beersList.collectLatest { pagedData ->
                beerListAdapter.submitData(pagedData)
            }
        }
        beerListAdapter.notifyDataSetChanged()
    }

}