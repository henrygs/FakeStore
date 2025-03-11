package com.henry.fakestore.modules.home.domain.usecase.remoto

import com.henry.fakestore.modules.home.domain.model.ProductsResult
import com.henry.fakestore.modules.home.domain.repository.remoto.ProductHomeRemoteRepository

class ProductHomeRemoteUseCaseImpl(
    private val repository: ProductHomeRemoteRepository
): ProductHomeRemoteUseCase {

    override suspend fun getProductsRemote(): ProductsResult {
        return try {
            repository.getProductsRemote()
        }catch (e : Exception){
            ProductsResult()
        }
    }
}