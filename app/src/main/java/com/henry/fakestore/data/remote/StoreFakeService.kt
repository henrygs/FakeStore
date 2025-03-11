package com.henry.fakestore.data.remote

import com.henry.fakestore.data.constants.Constants
import com.henry.fakestore.modules.home.domain.model.ProductsResult
import retrofit2.http.GET

interface StoreFakeService {
    @GET(Constants.PRODUCTS)
    suspend fun getProducts(): ProductsResult
}