package com.ravirawal.bheroassignment.retrofit

import com.ravirawal.bheroassignment.network.model.BeerListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Fetch List of beers news using GET API Call on given Url
     * Url would be something like this v2/beers?page=1&per_page=20
     */
    @GET(API_BEER)
    suspend fun getBeersList(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Response<BeerListResponse>
}