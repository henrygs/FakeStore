package com.henry.fakestore.modules.home.domain.repository.local

import com.henry.fakestore.data.local.dao.ProductDao
import com.henry.fakestore.data.local.model.Product

class ProductHomeLocalRepositoryImpl(
    private val productDao: ProductDao
): ProductHomeLocalRepository {
    override suspend fun saveProduct(products: Product) =
        productDao.insertProduct(products)

    override suspend fun getProductsLocal(): List<Product> =
        productDao.getAllProducts()
}