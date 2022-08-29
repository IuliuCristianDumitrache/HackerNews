package com.example.hackernews.data

import androidx.room.*

@Dao
interface NewsDao {
    @Query("SELECT * FROM NEWS WHERE NEWS_ID = :id")
    fun getNewsById(id: Int): NewsEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topStory: NewsEntity)
}