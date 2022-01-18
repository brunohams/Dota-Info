package com.codingwithmitch.hero_interactors

import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.hero_datasource_test.cache.HeroCacheFake
import com.codingwithmitch.hero_datasource_test.cache.HeroDatabaseFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceFake
import com.codingwithmitch.hero_datasource_test.network.HeroServiceResponseType
import com.codingwithmitch.hero_domain.Hero
import io.cucumber.java8.En
import io.cucumber.java8.PendingException
import io.cucumber.java8.StepDefinitionBody
import io.cucumber.java8.StepDefinitionBody.A0
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

class HeroesStepDefs: En {

    // setup
    val heroDatabase = HeroDatabaseFake()
    val heroCacheFake = HeroCacheFake(heroDatabase)
    val heroServiceFake = HeroServiceFake().build(
        type = HeroServiceResponseType.GoodData
    )
    val getHeroes = GetHeroes(
        cache = heroCacheFake,
        service = heroServiceFake
    )

    var listOfHeroes: List<Hero> = emptyList()

    init {

        When("Users requests api list data") {
            listOfHeroes = runBlocking {
                val emissions = getHeroes.execute().toList()
                assert(emissions[1] is DataState.Data)
                return@runBlocking (emissions[1] as DataState.Data<List<Hero>>).data!!
            }
        }
        Then(
            "Should return the a list with {int} heroes"
        ) { int1: Int? ->
            assertEquals(int1, listOfHeroes.size)
        }

        Then(
            "The heroes id order should be ascending"
        ) {
            // assert is ascending
            for (index in 1 until listOfHeroes.size) {
                val currentHeroId = listOfHeroes[index-1].id
                val nextHeroId = listOfHeroes[index].id
                assertTrue(currentHeroId < nextHeroId)
                println("${listOfHeroes[index-1].localizedName} id is bigger than ${listOfHeroes[index].localizedName} id")
            }
        }


    }

}