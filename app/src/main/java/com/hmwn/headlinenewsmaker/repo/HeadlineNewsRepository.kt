package com.hmwn.headlinenewsmaker.repo

import com.hmwn.headlinenewsmaker.data.local.LocalDatabase
import com.hmwn.headlinenewsmaker.data.local.dao.HeadlineNewsDao
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity

class HeadlineNewsRepository(
    private val localDatabase: LocalDatabase
) {

    suspend fun insert(headline: HeadlineNewsEntity): Long {
        return localDatabase.headlineNewsDao().insertArticle(headline)
    }

    suspend fun getAll() = localDatabase.headlineNewsDao().getAllHeadlineNews()

    suspend fun getById(id: Int) = localDatabase.headlineNewsDao().getHeadlineNewsById(id)

    suspend fun update(headline: HeadlineNewsEntity) = localDatabase.headlineNewsDao().updateHeadlineNews(headline)

    suspend fun delete(headline: HeadlineNewsEntity) = localDatabase.headlineNewsDao().deleteHeadlineNews(headline)

}