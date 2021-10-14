package com.ravirawal.bheroassignment.beer_list_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ravirawal.bheroassignment.databinding.LayoutTabTwoBinding
import com.ravirawal.bheroassignment.model_ui.BeerDiffCallBack
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.utils.loadImage

class BeerListAdapter(private val onClick: (BeerUi, Int) -> Unit = { _, _ -> run {} }) :
    PagingDataAdapter<BeerUi, BeerListAdapter.BeerTwoViewHolder>(BeerDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BeerTwoViewHolder(
        LayoutTabTwoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: BeerTwoViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class BeerTwoViewHolder(
        private val binding: LayoutTabTwoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beerUi: BeerUi?, onClick: (BeerUi, Int) -> Unit) {
            beerUi?.let {
                with(binding) {
                    imageViewPrimaryTabTwo.loadImage(it.imageUrl)
                    textViewTitleTabTwo.text = it.name

                    checkboxSelectedTabTwo.setOnCheckedChangeListener{ _, _ ->{}}
                    checkboxSelectedTabTwo.isChecked = it.isChecked

                    checkboxSelectedTabTwo.setOnCheckedChangeListener { _, isChecked ->
                        it.isChecked = isChecked
                        onClick(it, checkboxSelectedTabTwo.id)
                    }

                    root.setOnClickListener { _ ->
                        onClick(it, root.id)
                    }
                }
            }
        }
    }
}
