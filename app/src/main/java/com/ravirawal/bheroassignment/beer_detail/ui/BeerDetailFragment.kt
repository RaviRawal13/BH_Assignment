package com.ravirawal.bheroassignment.beer_detail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.ravirawal.bheroassignment.R
import com.ravirawal.bheroassignment.beer_list_one.ui.BeerListOneFragment
import com.ravirawal.bheroassignment.beer_list_two.ui.BeerListTwoFragment
import com.ravirawal.bheroassignment.databinding.FragmentBeerDetailBinding
import com.ravirawal.bheroassignment.databinding.FragmentViewPagerBinding
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.network.data_source.BeerSources
import com.ravirawal.bheroassignment.retrofit.ServiceHelper
import com.ravirawal.bheroassignment.shared_vm.BeerListViewModel
import com.ravirawal.bheroassignment.shared_vm.BeerListViewModelFactory
import com.ravirawal.bheroassignment.utils.loadImage
import com.ravirawal.bheroassignment.view_pager.adapter.ViewPagerAdapter

class BeerDetailFragment : Fragment(R.layout.fragment_beer_detail) {

    private lateinit var binding: FragmentBeerDetailBinding

    private lateinit var viewModel: BeerListViewModel

    private val beerItem: BeerUi? by lazy {
        arguments?.let {
            val args = BeerDetailFragmentArgs.fromBundle(it)
            return@lazy args.beerItem
        }
        return@lazy null
    }


    private val beerListViewModelFactory by lazy {
        BeerListViewModelFactory(
            BeerSources(
                ServiceHelper.getAPIHelper()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBeerDetailBinding.bind(view)

        activity?.let {
            viewModel =
                ViewModelProvider(
                    it,
                    beerListViewModelFactory
                ).get(BeerListViewModel::class.java)
        }

        with(binding) {
            beerItem?.let {
                checkboxSelectedBeerDetail.isChecked = it.isChecked
                imageViewPrimaryBeerDetail.loadImage(it.imageUrl)
                textViewTitleBeerDetail.text = it.name
            }

            checkboxSelectedBeerDetail.setOnCheckedChangeListener { compoundButton, b ->

                beerItem?.let {
                    beerItem?.isChecked = b
                    viewModel.onNewEditRequest(it)
                }
            }

            imageButtonBackBeerDetail.setOnClickListener {
                activity?.onBackPressed()
            }
        }

    }

}

