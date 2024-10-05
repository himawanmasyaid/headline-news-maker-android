package com.hmwn.headlinenewsmaker.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE headline_news ADD COLUMN description TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE headline_news ADD COLUMN template_id INTEGER NOT NULL DEFAULT 0")
    }
}