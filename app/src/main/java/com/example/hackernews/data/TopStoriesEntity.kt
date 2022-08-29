package com.example.hackernews.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_STORIES", indices = [Index(value = ["NEWS_ID"], unique = true)])
data class TopStoriesEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "NEWS_ID")
    var newsId: Int = 0
)