package com.henry.fakestore.modules.favorite.domain.repository

import com.henry.fakestore.data.local.model.Product

interface ProductFavoriteRepository {
    suspend fun getProductsFavorite(): List<Product>
    suspend fun deleteProduct(product: Product)
}