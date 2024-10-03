package com.hmwn.headlinenewsmaker.di

import com.hmwn.headlinenewsmaker.view.headline.CreateHeadlineViewModel
import com.hmwn.headlinenewsmaker.view.main.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        MainViewModel(get())
    }

    single {
        CreateHeadlineViewModel(get())
    }

}