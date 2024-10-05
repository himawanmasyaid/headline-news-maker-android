package com.hmwn.headlinenewsmaker.repo

import com.hmwn.headlinenewsmaker.data.local.LocalDatabase
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.repo.datasource.HeadlineNewsDataSource

class HeadlineNewsRepository(
    private val database: LocalDatabase
): HeadlineNewsDataSource {

    override suspend fun insert(data: HeadlineNewsEntity): Long {
        return database.headlineNewsDao().insertHeadline(data)
    }

    override suspend fun getAllHeadlineNews(): List<HeadlineNewsEntity> {
        return database.headlineNewsDao().getAllHeadlineNews()
    }

    override suspend fun getHeadlineNewsById(id: Int): HeadlineNewsEntity {
        return database.headlineNewsDao().getHeadlineNewsById(id)
    }

    override suspend fun updateHeadlineNews(data: HeadlineNewsEntity) {
        database.headlineNewsDao().updateHeadlineNews(data)
    }

    override suspend fun deleteHeadlineNews(data: HeadlineNewsEntity) {
        database.headlineNewsDao().deleteHeadlineNews(data)
    }

    override suspend fun isHeadlineAvailable(headline: String): Boolean {
        return database.headlineNewsDao().isHeadlineAvailable(headline)
    }
}