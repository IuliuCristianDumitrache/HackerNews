package com.example.hackernews.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [(NewsEntity::class), (TopStoriesEntity::class)],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun topStoriesDao(): TopStoriesDao

    companion object {
        //Migrations
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS TOP_STORIES (
                             id INTEGER PRIMARY KEY NOT NULL,
                             NEWS_ID INTEGER NOT NULL DEFAULT ''
                             )
                             """.trimIndent()
                )
            }
        }
    }
}