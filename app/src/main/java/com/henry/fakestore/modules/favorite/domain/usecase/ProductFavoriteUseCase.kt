package com.henry.fakestore.modules.favorite.domain.usecase

import com.henry.fakestore.data.local.model.Product

interface ProductFavoriteUseCase {
    suspend fun getProductsFavorite(): List<Product>?
    suspend fun deleteProduct(product: Product)
}