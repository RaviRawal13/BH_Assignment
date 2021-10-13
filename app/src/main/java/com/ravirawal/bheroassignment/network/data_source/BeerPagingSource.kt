package com.ravirawal.bheroassignment.network.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ravirawal.bheroassignment.model_ui.BeerUi
import com.ravirawal.bheroassignment.network.model.BeerListResponse
import com.ravirawal.bheroassignment.network.model.BeerListResponseItem
import com.ravirawal.bheroassignment.network.Result
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 20

class BeerPagingSource(
    private val beerSources: BeerSources
) : PagingSource<Int, BeerUi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerUi> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response: Result<BeerListResponse> = beerSources.getBeersList(
                page = pageIndex,
                pageSize = NETWORK_PAGE_SIZE
            )
            val beers: BeerListResponse = (response as? Result.Success)?.data ?: arrayListOf()

            val beerUiList = beers.map { BeerUi(
                id = it.id,
                imageUrl = it.imageUrl,
                name = it.name,
                isChecked = false
            ) }
            val nextKey =
                if (beerUiList.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }

            LoadResult.Page(
                data = beerUiList,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, BeerUi>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}