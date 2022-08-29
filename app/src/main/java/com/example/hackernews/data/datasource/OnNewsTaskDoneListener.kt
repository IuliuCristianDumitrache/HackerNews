package com.example.hackernews.data.datasource

import com.example.hackernews.models.NewsModel

interface OnNewsTaskDoneListener {
    fun onNewsTaskDone(responseData: NewsModel)
    fun onNewsTaskError()
}