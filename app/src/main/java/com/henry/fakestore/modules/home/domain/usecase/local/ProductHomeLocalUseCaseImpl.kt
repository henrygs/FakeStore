package com.henry.fakestore.modules.home.domain.usecase.local

import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.modules.home.domain.repository.local.ProductHomeLocalRepository

class ProductHomeLocalUseCaseImpl(
    private val repository: ProductHomeLocalRepository
):ProductHomeLocalUseCase {

    override suspend fun saveProduct(products: Product) =
        repository.saveProduct(products)


    override suspend fun getProductsLocal(): List<Product> =
        repository.getProductsLocal()

}