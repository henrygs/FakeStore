package com.henry.fakestore.modules.home.domain.repository.remoto

import com.henry.fakestore.data.remote.StoreFakeService
import com.henry.fakestore.modules.home.domain.model.ProductsResult

class ProductsHomeRemoteRepositoryImpl(
    private val storeApi: StoreFakeService
): ProductHomeRemoteRepository {

    override suspend fun getProductsRemote(): ProductsResult =
        storeApi.getProducts()
}