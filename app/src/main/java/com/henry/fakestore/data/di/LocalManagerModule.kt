package com.henry.fakestore.data.di

import androidx.room.Room
import com.henry.fakestore.data.constants.Constants
import com.henry.fakestore.data.local.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object LocalManagerModule {
    fun moduleDao() = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDataBase::class.java,
                Constants.PRODUCT_DATABASE
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    fun moduleDataBase() = module {
        single { get<AppDataBase>().productDao() }
    }
}