package com.hmwn.headlinenewsmaker.repo.datasource

import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity

interface HeadlineNewsDataSource {

    suspend fun insert(data: HeadlineNewsEntity) : Long

    suspend fun getAllHeadlineNews(): List<HeadlineNewsEntity>

    suspend fun getHeadlineNewsById(id: Int): HeadlineNewsEntity

    suspend fun updateHeadlineNews(data: HeadlineNewsEntity)

    suspend fun deleteHeadlineNews(data: HeadlineNewsEntity)

    suspend fun isHeadlineAvailable(headline: String): Boolean

}