package com.codingwithmitch.hero_interactors
import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.core.domain.UIComponent
import com.codingwithmitch.hero_datasource_test.cache.HeroCacheFake
import com.codingwithmitch.hero_datasource_test.cache.HeroDatabaseFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceResponseType
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataValid
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataValid.NUM_HEROES
import com.codingwithmitch.hero_datasource_test.util.serializeHeroData
import com.codingwithmitch.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetHeroTest {

    private lateinit var getHero: GetHero

    @Test
    fun getHero_success() = runBlocking {
        // SETUP
        val database = HeroDatabaseFake()
        val cache = HeroCacheFake(database)
        val searchId = 9

        // populate cache
        cache.insert(
            heroes = serializeHeroData(HeroDataValid.data)
        )

        // execute use-case
        getHero = GetHero(
            cache = cache
        )

        val emissions = getHero.execute(
            searchId
        ).toList()

        // assert first emission
        assert(emissions[0] == DataState.Loading<Hero>(ProgressBarState.Loading))

        // assert second emission
        assert(emissions[1] is DataState.Data<Hero>)

        // assert data
        assert((emissions[1] as DataState.Data<Hero>).data?.id == searchId)

        // assert final emission
        assert(emissions[2] == DataState.Loading<Hero>(ProgressBarState.Idle))
    }

    @Test
    fun getHero_not_found() = runBlocking {
        // SETUP
        val database = HeroDatabaseFake()
        val cache = HeroCacheFake(database)
        val searchId = 100000

        // populate cache
        cache.insert(
            heroes = serializeHeroData(HeroDataValid.data)
        )

        // execute use-case
        getHero = GetHero(
            cache = cache
        )

        val emissions = getHero.execute(
            searchId
        ).toList()

        // assert first emission
        assert(emissions[0] == DataState.Loading<Hero>(ProgressBarState.Loading))

        // assert second emission
        assert(emissions[1] == DataState.Response<Hero>(UIComponent.Dialog("Error", "Hero doesn't exist")))

        // assert final emission
        assert(emissions[2] == DataState.Loading<Hero>(ProgressBarState.Idle))

    }

}

