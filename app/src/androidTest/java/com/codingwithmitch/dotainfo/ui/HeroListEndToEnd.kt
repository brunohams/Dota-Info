package com.codingwithmitch.dotainfo.ui

import com.codingwithmitch.dotainfo.coil.FakeImageLoader.Factory.build
import com.codingwithmitch.dotainfo.di.HeroInteractorsModule
import com.codingwithmitch.hero_datasource.cache.HeroCache
import com.codingwithmitch.hero_datasource.network.HeroService
import com.codingwithmitch.hero_datasource_test.cache.HeroCacheFake
import com.codingwithmitch.hero_datasource_test.cache.HeroDatabaseFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceResponseType
import com.codingwithmitch.hero_interactors.FilterHeroes
import com.codingwithmitch.hero_interactors.GetHero
import com.codingwithmitch.hero_interactors.GetHeroes
import com.codingwithmitch.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@UninstallModules(HeroInteractorsModule::class)
@HiltAndroidTest
class HeroListEndToEnd {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestHeroInteractorsModule{

        @Provides
        @Singleton
        fun provideHeroCache(): HeroCache {
            return HeroCacheFake(HeroDatabaseFake())
        }

        @Provides
        @Singleton
        fun provideHeroService(): HeroService {
            return HeroServiceFake().build(
                type = HeroServiceResponseType.GoodData
            )
        }

        @Provides
        @Singleton
        fun provideHeroInteractors(
            cache: HeroCache,
            service: HeroService
        ): HeroInteractors {
            return HeroInteractors(
                getHeroes = GetHeroes(
                    cache = cache,
                    service = service
                ),
                filterHeroes = FilterHeroes(),
                getHero = GetHero(cache)
            )
        }

    }
}