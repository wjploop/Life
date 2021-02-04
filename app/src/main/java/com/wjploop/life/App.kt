package com.wjploop.life

import android.app.Application
import com.wjploop.life.data.db.LifeDatabase

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        app = this
    }


    fun getDataBase() = LifeDatabase.getInstance(app)

    companion object {
        lateinit var app: App

        fun isAppInit() = ::app.isInitialized
    }
}