package com.example.seamlabstask.common

import android.app.Application
import com.example.seamlabstask.roomdatabase.LocalDataBase

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Database
        LocalDataBase.init(this)
    }

}