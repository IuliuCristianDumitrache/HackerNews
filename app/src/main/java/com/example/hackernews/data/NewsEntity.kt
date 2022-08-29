package com.example.hackernews.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.hackernews.models.NewsModel

@Entity(tableName = "NEWS", indices = [Index(value = ["NEWS_ID"], unique = true)])
data class NewsEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "NEWS_ID")
    var newsId: Int = 0,

    @ColumnInfo(name = "TITLE")
    var title: String = "",

    @ColumnInfo(name = "DATE")
    var date: Long = 0,

    @ColumnInfo(name = "URL")
    var url: String = ""
) {
    fun toNewsModel(): NewsModel {
        return NewsModel(id = newsId, title = title, time = date, url = url)
    }
}