package com.henry.fakestore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henry.fakestore.data.local.dao.ProductDao
import com.henry.fakestore.data.local.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun productDao(): ProductDao
}