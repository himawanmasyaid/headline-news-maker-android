package com.hmwn.headlinenewsmaker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity

@Dao
interface HeadlineNewsDao {

    @Insert
    suspend fun insertArticle(article: HeadlineNewsEntity)

    @Query("SELECT * FROM headline_news")
    suspend fun getAllHeadlineNews(): List<HeadlineNewsEntity>

    @Query("SELECT * FROM headline_news WHERE id = :id")
    suspend fun getHeadlineNewsById(id: Int): HeadlineNewsEntity

    @Update
    suspend fun updateHeadlineNews(article: HeadlineNewsEntity)

    @Delete
    suspend fun deleteHeadlineNews(article: HeadlineNewsEntity)

}