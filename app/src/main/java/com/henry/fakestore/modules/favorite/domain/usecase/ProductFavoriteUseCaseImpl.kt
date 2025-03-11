package com.henry.fakestore.modules.favorite.domain.usecase

import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.modules.favorite.domain.repository.ProductFavoriteRepository

class ProductFavoriteUseCaseImpl(
    private val repository: ProductFavoriteRepository
): ProductFavoriteUseCase {
    override suspend fun getProductsFavorite(): List<Product> {
        return try {
            repository.getProductsFavorite()
        } catch (exception: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteProduct(product: Product) {
        return repository.deleteProduct(product)
    }
}