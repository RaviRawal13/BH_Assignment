package com.ravirawal.bheroassignment.network.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

typealias BeerListResponse = ArrayList<BeerListResponseItem>

data class BeerListResponseItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
)
