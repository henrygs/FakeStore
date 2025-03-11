package com.henry.fakestore.data.di

import com.henry.fakestore.data.constants.Constants
import com.henry.fakestore.data.remote.StoreFakeService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteManagerModule {

    fun moduleRetrofit() = module {
        factory { getRetrofit() }
    }

    private fun getRetrofit(): StoreFakeService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StoreFakeService::class.java)
    }
}


