package com.hmwn.headlinenewsmaker.repo

import com.hmwn.headlinenewsmaker.data.local.LocalDatabase
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.repo.datasource.HeadlineNewsDataSource

class HeadlineNewsRepository(
    private val database: LocalDatabase
): HeadlineNewsDataSource {

    override suspend fun insert(headline: HeadlineNewsEntity): Long {
        return database.headlineNewsDao().insertHeadline(headline)
    }

    override suspend fun getAllHeadlineNews(): List<HeadlineNewsEntity> {
        return database.headlineNewsDao().getAllHeadlineNews()
    }

    override suspend fun getHeadlineNewsById(id: Int): HeadlineNewsEntity {
        return database.headlineNewsDao().getHeadlineNewsById(id)
    }

    override suspend fun updateHeadlineNews(headline: HeadlineNewsEntity) {
        database.headlineNewsDao().updateHeadlineNews(headline)
    }

    override suspend fun deleteHeadlineNews(headline: HeadlineNewsEntity) {
        database.headlineNewsDao().deleteHeadlineNews(headline)
    }

    override suspend fun isHeadlineAvailable(headline: String): Boolean {
        return database.headlineNewsDao().isHeadlineAvailable(headline)
    }
}