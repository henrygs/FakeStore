package com.henry.fakestore.modules.home.domain.usecase.local

import com.henry.fakestore.data.local.model.Product

interface ProductHomeLocalUseCase {
    suspend fun saveProduct(products: Product)
    suspend fun getProductsLocal(): List<Product>?
}