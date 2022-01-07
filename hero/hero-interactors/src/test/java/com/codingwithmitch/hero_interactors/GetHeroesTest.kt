package com.codingwithmitch.hero_interactors
import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.hero_datasource_test.cache.HeroCacheFake
import com.codingwithmitch.hero_datasource_test.cache.HeroDatabaseFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceResponseType
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataValid.NUM_HEROES
import com.codingwithmitch.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetHeroesTest {

    // system in test
    private lateinit var getHeroes: GetHeroes

    @Test
    fun getHeroes_success() = runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCacheFake = HeroCacheFake(heroDatabase)
        val heroServiceFake = HeroServiceFake().build(
            type = HeroServiceResponseType.GoodData
        )

        getHeroes = GetHeroes(
            cache = heroCacheFake,
            service = heroServiceFake
        )

        // confirm that the cache is empty before any use case has been execute
        var cachedHeroes = heroCacheFake.selectAll()
        assert(cachedHeroes.isEmpty())

        // execute the use-case
        val emissions = getHeroes.execute().toList()

        // first emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // second emission should be data
        assert(emissions[1] is DataState.Data)

        val data = (emissions[1] as DataState.Data<List<Hero>>).data
        val sizeOfData = data?.size ?: 0

        // confirm the cache is no longer empty
        cachedHeroes = heroCacheFake.selectAll()

        // assert the same size in cache, as returned data
        assert(cachedHeroes.size == sizeOfData)

        // Assert that the loading state is not more loading
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))

    }
}

