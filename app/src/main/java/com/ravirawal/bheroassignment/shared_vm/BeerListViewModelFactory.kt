package com.ravirawal.bheroassignment.shared_vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.bheroassignment.network.data_source.BeerSources

@Suppress("UNCHECKED_CAST")
class BeerListViewModelFactory(
    private val api: BeerSources
) : ViewModelProvider.NewInstanceFactory(){
 
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BeerListViewModel(api) as T
    }
}