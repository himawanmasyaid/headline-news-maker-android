package com.hmwn.headlinenewsmaker

import android.app.Application
import com.hmwn.headlinenewsmaker.di.appModule
import com.hmwn.headlinenewsmaker.di.repositoryModule
import com.hmwn.headlinenewsmaker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HeadlineApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@HeadlineApp)
            modules(
                arrayListOf(
                    appModule, viewModelModule, repositoryModule
                )
            )
        }
    }

}