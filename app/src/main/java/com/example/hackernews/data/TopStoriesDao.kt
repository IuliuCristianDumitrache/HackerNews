package com.example.hackernews.data

import androidx.room.*

@Dao
interface TopStoriesDao {

    @Query("SELECT NEWS_ID FROM TOP_STORIES")
    fun getTopStories(): List<Int>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topStory: TopStoriesEntity)
}