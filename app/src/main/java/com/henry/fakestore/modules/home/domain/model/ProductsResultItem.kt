package com.henry.fakestore.modules.home.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsResultItem(
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rating")
    val rating: Rating?,
    @SerializedName("title")
    val title: String?,
    var isFavorite: Boolean = false
): Serializable