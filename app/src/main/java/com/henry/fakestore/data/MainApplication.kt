package com.henry.fakestore.data

import android.app.Application
import com.henry.fakestore.modules.koinModules.ApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            ApplicationModule.loadModules()
        }
    }
}