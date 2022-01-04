package com.codingwithmitch.ui_herodetail.di

import com.codingwithmitch.hero_interactors.GetHero
import com.codingwithmitch.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    @Provides
    @Singleton
    fun provideGetHero(
        interactors: HeroInteractors
    ): GetHero {
        return interactors.getHero
    }
}