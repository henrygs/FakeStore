package com.henry.fakestore.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.henry.fakestore.modules.home.domain.model.Rating
import java.io.Serializable
import java.text.NumberFormat
import java.util.Locale

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int?,
    val category: String?,
    val image: String?,
    val price: String?,
    val count: String?,
    val rating: String?,
    val title: String?,
    val description: String?,
    var isFavorite: Boolean = false
): Serializable