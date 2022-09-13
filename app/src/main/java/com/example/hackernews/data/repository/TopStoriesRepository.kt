package com.example.hackernews.data.repository

import com.example.hackernews.data.datasource.*
import com.example.hackernews.utils.connectivity.NetworkConnection

class TopStoriesRepository {
    fun fetchTopStories(topStoriesTaskListener: OnTopStoriesTaskDoneListener) {
        if (NetworkConnection.isConnected) {
            GetTopStoriesRemoteTask(topStoriesTaskListener).execute()
        } else {
            GetTopStoriesDBTask(topStoriesTaskListener).execute()
        }
    }

    fun fetchNewsFromTopStories(
        newsTaskListener: OnNewsTaskDoneListener,
        id: Int?
    ) {
        GetNewsRemoteTask(newsTaskListener).execute(id)
    }

    fun fetchTopStoriesFromDB(topStoriesTaskListener: OnTopStoriesTaskDoneListener) {
        GetTopStoriesDBTask(topStoriesTaskListener).execute()
    }
}