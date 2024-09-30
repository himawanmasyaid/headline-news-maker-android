package com.hmwn.headlinenewsmaker.di

import androidx.room.Room
import com.hmwn.headlinenewsmaker.BuildConfig
import com.hmwn.headlinenewsmaker.ads.AdsManager
import com.hmwn.headlinenewsmaker.common.ImagePicker
import com.hmwn.headlinenewsmaker.data.local.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(androidContext(), LocalDatabase::class.java, "headline-db")
            .build()
    }

    single { ImagePicker(androidContext(), "${BuildConfig.APPLICATION_ID}.provider") }

    single { AdsManager(androidContext()) }

}