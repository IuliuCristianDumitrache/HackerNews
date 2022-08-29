package com.example.hackernews.models

import android.os.Parcelable
import com.example.hackernews.data.NewsEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val by: String? = null,
    val descendants: Int? = null,
    val id: Int? = null,
    val kids: ArrayList<Int>? = null,
    val score: Int? = null,
    val time: Long? = null,
    val title: String? = null,
    val type: String? = null,
    val url: String? = null,
    val isError: Boolean = false
) : Parcelable {
    fun getTimeAsMillis(): Long {
        return (this.time ?: 0) * 1000
    }

    fun mapNewsModelToNewsEntity(): NewsEntity {
        return NewsEntity(
            newsId = id ?: 0,
            title = title ?: "",
            date = time ?: 0L,
            url = url ?: ""
        )
    }
}