package com.hmwn.headlinenewsmaker.di

import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        HeadlineNewsRepository(get())
    }

}