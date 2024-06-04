package com.hmwn.headlinenewsmaker.di

import com.hmwn.headlinenewsmaker.view.createnews.CreateNewsViewModel
import com.hmwn.headlinenewsmaker.view.main.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        CreateNewsViewModel(get())
    }

    single {
        MainViewModel(get())
    }

}