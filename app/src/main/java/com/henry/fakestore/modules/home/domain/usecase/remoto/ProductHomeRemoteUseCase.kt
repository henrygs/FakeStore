package com.henry.fakestore.modules.home.domain.usecase.remoto

import com.henry.fakestore.modules.home.domain.model.ProductsResult

interface ProductHomeRemoteUseCase {
    suspend fun getProductsRemote(): ProductsResult
}