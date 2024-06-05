package com.hmwn.headlinenewsmaker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "headline_news")
data class HeadlineNewsEntity(
    @ColumnInfo(name = "headline")
    val headline: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "datetime")
    val datetime: String,
    @ColumnInfo(name = "image")
    val image: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)