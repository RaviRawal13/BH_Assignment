package com.ravirawal.bheroassignment.network.data_source

import com.ravirawal.bheroassignment.network.model.BeerListResponse
import com.ravirawal.bheroassignment.network.Error
import com.ravirawal.bheroassignment.network.Result
import com.ravirawal.bheroassignment.retrofit.IApiHelper

class BeerSources(private val service: IApiHelper) {

    suspend fun getBeersList(
        page: Int? = null,
        pageSize: Int? = null
    ): Result<BeerListResponse> {
        val response = service.getBeersList(page, pageSize)
        return if (response.isSuccessful && response.body() is BeerListResponse) {
            Result.Success(
                response.body() as BeerListResponse,
                response.message(),
                response.code()
            )
        } else {
            Result.Failure(
                Error.APIError,
                response.message(),
                response.code()
            )
        }
    }

}
