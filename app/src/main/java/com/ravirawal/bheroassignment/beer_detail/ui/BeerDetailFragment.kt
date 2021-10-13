package com.ravirawal.bheroassignment.beer_detail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.ravirawal.bheroassignment.R
import com.ravirawal.bheroassignment.beer_list_one.ui.BeerListOneFragment
import com.ravirawal.bheroassignment.beer_list_two.ui.BeerListTwoFragment
import com.ravirawal.bheroassignment.databinding.FragmentBeerDetailBinding
import com.ravirawal.bheroassignment.databinding.FragmentViewPagerBinding
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.utils.loadImage
import com.ravirawal.bheroassignment.view_pager.adapter.ViewPagerAdapter

class BeerDetailFragment : Fragment(R.layout.fragment_beer_detail) {

    private lateinit var binding: FragmentBeerDetailBinding

    private val beerItem: BeerUi? by lazy {
        arguments?.let {
            val args = BeerDetailFragmentArgs.fromBundle(it)
            return@lazy args.beerItem
        }
        return@lazy null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBeerDetailBinding.bind(view)

        with(binding) {
            beerItem?.let {
                checkboxSelectedBeerDetail.isChecked = it.isChecked
                imageViewPrimaryBeerDetail.loadImage(it.imageUrl)
                textViewTitleBeerDetail.text = it.name
            }

            imageButtonBackBeerDetail.setOnClickListener {
                activity?.onBackPressed()
            }
        }

    }

}

