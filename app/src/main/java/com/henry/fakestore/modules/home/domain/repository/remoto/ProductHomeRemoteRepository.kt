package com.henry.fakestore.modules.home.domain.repository.remoto

import com.henry.fakestore.modules.home.domain.model.ProductsResult

interface ProductHomeRemoteRepository {
    suspend fun getProductsRemote(): ProductsResult
}