package com.hieulh.test.demoproj2.ui

import android.app.Application
import com.hieulh.test.demoproj2.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger(Level.ERROR)
            //inject Android context
            androidContext(this@MainApplication)
            // use modules
            modules(appModules)
        }
    }
}