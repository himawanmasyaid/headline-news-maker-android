package com.hmwn.headlinenewsmaker.di

import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import com.hmwn.headlinenewsmaker.repo.datasource.HeadlineNewsDataSource
import org.koin.dsl.module

val repositoryModule = module {

    single<HeadlineNewsDataSource> { HeadlineNewsRepository(get()) }

}