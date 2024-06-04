package com.hmwn.headlinenewsmaker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hmwn.headlinenewsmaker.data.local.dao.HeadlineNewsDao
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity

@Database(
    entities = [HeadlineNewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun headlineNewsDao(): HeadlineNewsDao

}