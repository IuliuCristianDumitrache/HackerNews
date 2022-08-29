package com.example.hackernews.data

import android.content.Context
import androidx.room.Room
import com.example.hackernews.data.AppDatabase.Companion.MIGRATION_1_2

object DatabaseHelper {
    lateinit var database: AppDatabase

    private const val DB_NAME = "HACKER_NEWS_DB"

    fun initDB(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}