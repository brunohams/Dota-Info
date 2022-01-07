package com.codingwithmitch.ui_herodetail.di

import com.codingwithmitch.core.util.Logger
import com.codingwithmitch.hero_interactors.GetHero
import com.codingwithmitch.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    @Provides
    @Singleton
    @Named("heroDetailLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "HeroDetailViewModel",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHero(
        interactors: HeroInteractors
    ): GetHero {
        return interactors.getHero
    }
}