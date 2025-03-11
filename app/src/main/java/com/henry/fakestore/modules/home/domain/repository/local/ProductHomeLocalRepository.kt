package com.henry.fakestore.modules.home.domain.repository.local

import com.henry.fakestore.data.local.model.Product

interface ProductHomeLocalRepository {
    suspend fun saveProduct(products: Product)
    suspend fun getProductsLocal(): List<Product>
}