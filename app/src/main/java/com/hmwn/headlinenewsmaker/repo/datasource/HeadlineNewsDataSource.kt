package com.hmwn.headlinenewsmaker.repo.datasource

import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity

interface HeadlineNewsDataSource {

    suspend fun insert(article: HeadlineNewsEntity) : Long

    suspend fun getAllHeadlineNews(): List<HeadlineNewsEntity>

    suspend fun getHeadlineNewsById(id: Int): HeadlineNewsEntity

    suspend fun updateHeadlineNews(article: HeadlineNewsEntity)

    suspend fun deleteHeadlineNews(article: HeadlineNewsEntity)

    suspend fun isHeadlineAvailable(headline: String): Boolean

}