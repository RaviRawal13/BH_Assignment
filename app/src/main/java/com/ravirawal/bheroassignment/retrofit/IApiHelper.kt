package com.ravirawal.bheroassignment.retrofit

import com.ravirawal.bheroassignment.network.model.BeerListResponse
import retrofit2.Response

interface IApiHelper {

    suspend fun getBeersList(
        page: Int? = null,
        pageSize: Int? = null
    ): Response<BeerListResponse>

    class ApiHelperImpl(private val apiService: ApiService) : IApiHelper {

        override suspend fun getBeersList(
            page: Int?,
            pageSize: Int?
        ): Response<BeerListResponse> {
            return apiService.getBeersList(page, pageSize)
        }
    }
}
