package com.codingwithmitch.ui_herolist.di

import com.codingwithmitch.core.Logger
import com.codingwithmitch.hero_interactors.GetHeroes
import com.codingwithmitch.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "HeroListViewModel",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHeroes(
        interactors: HeroInteractors
    ): GetHeroes {
        return interactors.getHeroes
    }

}