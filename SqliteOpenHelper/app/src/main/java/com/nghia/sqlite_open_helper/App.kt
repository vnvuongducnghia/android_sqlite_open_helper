package com.nghia.sqlite_open_helper

import android.app.Application
import android.content.Context

/**
 * Created by nghia.vuong on 04,May,2021
 */
class App : Application() {

    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}