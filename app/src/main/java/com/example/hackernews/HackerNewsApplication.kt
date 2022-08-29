package com.example.hackernews

import android.app.Application
import com.example.hackernews.data.DatabaseHelper.initDB


class HackerNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDB(applicationContext)
    }
}