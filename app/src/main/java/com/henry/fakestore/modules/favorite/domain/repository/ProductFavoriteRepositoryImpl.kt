package com.henry.fakestore.modules.favorite.domain.repository

import com.henry.fakestore.data.local.dao.ProductDao
import com.henry.fakestore.data.local.model.Product

class ProductFavoriteRepositoryImpl(
    private val productDao: ProductDao
): ProductFavoriteRepository {
    override suspend fun getProductsFavorite(): List<Product> =
        productDao.getAllProducts()

    override suspend fun deleteProduct(product: Product) =
        productDao.deleteProduct(product)
}