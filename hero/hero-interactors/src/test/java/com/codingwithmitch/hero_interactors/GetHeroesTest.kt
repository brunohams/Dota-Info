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

    @Test
    fun getHeroes_malformedData_but_successFromCache() = runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCacheFake = HeroCacheFake(heroDatabase)
        val heroServiceFake = HeroServiceFake().build(
            type = HeroServiceResponseType.Malformed
        )

        getHeroes = GetHeroes(
            cache = heroCacheFake,
            service = heroServiceFake
        )

        // confirm that the cache is empty before any use case has been execute
        var cachedHeroes = heroCacheFake.selectAll()
        assert(cachedHeroes.isEmpty())

        // Add some data to the cache
        val heroData = serializeHeroData(HeroDataValid.data)
        heroCacheFake.insert(heroData)

        // confirm that the cache is not empty
        assert(cachedHeroes.isNotEmpty())

        // execute the use-case
        val emissions = getHeroes.execute().toList()

        // first emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // confirm the second emission is an error response
        assert(emissions[1] is DataState.Response)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Network Data Error")

        // Confirm that the message is json error
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains("Unexpected JSON token at offset"))

        // confirm third emission is data from the cache
        assert(emissions[2] is DataState.Data)
        val data = (emissions[2] as DataState.Data<List<Hero>>).data
        val sizeOfData = data?.size ?: 0

        // assert the size of data
        assert(sizeOfData == heroData.size)

        // Assert that the loading state is not more loading
        assert(emissions[3] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))

    }
}

