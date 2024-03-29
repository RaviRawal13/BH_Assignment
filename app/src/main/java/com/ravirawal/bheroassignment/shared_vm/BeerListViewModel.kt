package com.ravirawal.bheroassignment.shared_vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.network.data_source.BeerPagingSource
import com.ravirawal.bheroassignment.network.data_source.BeerSources
import com.ravirawal.bheroassignment.network.data_source.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

private const val PREFETCH_DISTANCE = 5

class BeerListViewModel(
    private val beerSources: BeerSources,
) : ViewModel() {

    var selectedTab: Int = 0

    private val pagingConfig = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        prefetchDistance = PREFETCH_DISTANCE,
        enablePlaceholders = true
    )

    fun onNewEditRequest(beerUi: BeerUi) {
        modificationEvents.value += Pair(beerUi.id ?: -1, beerUi.isChecked)
    }

    private val modificationEvents = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    private val beerPagingData: Pager<Int, BeerUi> = Pager(pagingConfig) {
        BeerPagingSource(beerSources)
    }

    val beersList: Flow<PagingData<BeerUi>> = beerPagingData
        .flow
        .cachedIn(viewModelScope)
        .combine(modificationEvents) { pagingData, modifications ->
            edit(pagingData, modifications)
        }

    private fun edit(
        paging: PagingData<BeerUi>,
        beerUi: Map<Int, Boolean>
    ): PagingData<BeerUi> {
        return paging
            .map {
                if (beerUi.containsKey(it.id))
                    return@map it.copy(isChecked = beerUi[it.id] == true)
                else
                    return@map it
            }
    }
}