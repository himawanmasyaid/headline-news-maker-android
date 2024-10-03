package com.hmwn.headlinenewsmaker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hmwn.headlinenewsmaker.data.local.dao.HeadlineNewsDao
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity

@Database(
    entities = [HeadlineNewsEntity::class],
    version = 2,
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun headlineNewsDao(): HeadlineNewsDao

}

