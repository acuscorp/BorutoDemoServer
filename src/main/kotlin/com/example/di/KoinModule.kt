package com.example.di

import com.example.repostory.HeroRepository
import com.example.repostory.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
}