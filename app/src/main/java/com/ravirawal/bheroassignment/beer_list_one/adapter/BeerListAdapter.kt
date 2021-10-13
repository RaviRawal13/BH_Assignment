package com.ravirawal.bheroassignment.beer_list_one.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ravirawal.bheroassignment.databinding.LayoutTabOneBinding
import com.ravirawal.bheroassignment.model_ui.BeerDiffCallBack
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.utils.loadImage

class BeerListAdapter(private val onClick: (BeerUi, Int) -> Unit = { _, _ -> run {} }) :
    PagingDataAdapter<BeerUi, BeerListAdapter.BeerOneViewHolder>(
        BeerDiffCallBack
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BeerOneViewHolder(
        LayoutTabOneBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: BeerOneViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class BeerOneViewHolder(
        private val binding: LayoutTabOneBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beerUi: BeerUi?, onClick: (BeerUi, Int) -> Unit) {
            beerUi?.let {
                with(binding) {
                    imageViewPrimaryTabOne.loadImage(it.imageUrl)
                    textViewTitleTabOne.text = it.name

                    checkboxSelectedTabOne.isChecked = it.isChecked ?: false

                    checkboxSelectedTabOne.setOnCheckedChangeListener { _, _ ->
                        it.isChecked = !(it.isChecked ?: true)
                        onClick(it, checkboxSelectedTabOne.id)
                    }

                    root.setOnClickListener { _ ->
                        onClick(it, root.id)
                    }
                }
            }
        }
    }
}

