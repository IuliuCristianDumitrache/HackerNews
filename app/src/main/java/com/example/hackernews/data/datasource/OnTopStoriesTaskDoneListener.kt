package com.example.hackernews.data.datasource

interface OnTopStoriesTaskDoneListener {
    fun onTaskDone(responseData: ArrayList<Int>?)
    fun onError()
}