package com.codingwithmitch.hero_interactors
import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.core.domain.FilterOrder
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
import com.codingwithmitch.hero_domain.HeroAttribute
import com.codingwithmitch.hero_domain.HeroFilter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.math.round

class FilterHeroesTest {

    private lateinit var filterHeroes: FilterHeroes

    @Test
    fun filterHeroes_by_name_found_multiple() = runBlocking {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = "Earth"

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert if is multiple
        assert(filteredHeroes.isNotEmpty())

        // assert result
        assert(filteredHeroes[0].localizedName.contains("Earth"))

        filteredHeroes.forEach {
            println(it)
        }
    }

    @Test
    fun filterHeroes_by_name_found_single() = runBlocking {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = "Rubick"

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert result
        assert(filteredHeroes[0].localizedName == "Rubick")

        print(filteredHeroes)
    }

    @Test
    fun filterHeroes_by_name_found_none() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = "Xatuba"

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert result
        assert(filteredHeroes.isEmpty())

        print(filteredHeroes)
    }

    @Test
    fun filterHeroes_order_by_desc() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(FilterOrder.Descending),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert is descending
        for (index in 1 until filteredHeroes.size) {
            val firstCharOfNextHero = filteredHeroes[index-1].localizedName.toCharArray()[0]
            val firstCharOfCurrentHero = filteredHeroes[index].localizedName.toCharArray()[0]
            assert(firstCharOfNextHero >= firstCharOfCurrentHero)
            println("${filteredHeroes[index-1].localizedName} is bigger or equals than ${filteredHeroes[index].localizedName}")
        }

        print(filteredHeroes)
    }

    @Test
    fun filterHeroes_order_by_asc() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(FilterOrder.Ascending),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert is ascending
        for (index in 1 until filteredHeroes.size) {
            val firstCharOfNextHero = filteredHeroes[index-1].localizedName.toCharArray()[0]
            val firstCharOfCurrentHero = filteredHeroes[index].localizedName.toCharArray()[0]
            assert(firstCharOfCurrentHero >= firstCharOfNextHero)
            println("${filteredHeroes[index-1].localizedName} is smaller or equals than ${filteredHeroes[index].localizedName}")
        }

        print(filteredHeroes)
    }

    @Test
    fun filterHeroes_by_strength_attribute() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(),
            attributeFilter = HeroAttribute.Strength
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert if all are strength
        filteredHeroes.forEach {
            assert(it.primaryAttribute is HeroAttribute.Strength)
            println(it)
        }

    }

    @Test
    fun filterHeroes_by_agility_attribute() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(),
            attributeFilter = HeroAttribute.Agility
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert if all are strength
        filteredHeroes.forEach {
            assert(it.primaryAttribute is HeroAttribute.Agility)
            println(it)
        }

    }

    @Test
    fun filterHeroes_by_intelligence_attribute() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.Alphabetic(),
            attributeFilter = HeroAttribute.Intelligence
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert if all are strength
        filteredHeroes.forEach {
            assert(it.primaryAttribute is HeroAttribute.Intelligence)
            println(it)
        }

    }

    @Test
    fun filterHeroes_by_pro_win_asc() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.ProWins(FilterOrder.Ascending),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert is ascending
        for (index in 1 until filteredHeroes.size) {
            val nextHero = filteredHeroes[index-1]
            val winRateNextHero = round(nextHero.proWins.toDouble() / nextHero.proPick.toDouble() * 100).toInt()

            val currentHero = filteredHeroes[index]
            val winRateCurrentHero = round(currentHero.proWins.toDouble() / currentHero.proPick.toDouble() * 100).toInt()

            assert(winRateCurrentHero >= winRateNextHero)
            println("$winRateCurrentHero is bigger or equals than $winRateNextHero")
        }

    }

    @Test
    fun filterHeroes_by_pro_win_desc() {
        // SETUP
        val listOfHeroes = serializeHeroData(HeroDataValid.data)
        val searchQuery = ""

        filterHeroes = FilterHeroes()

        // execute use-case
        val filteredHeroes = filterHeroes.execute(
            current = listOfHeroes,
            searchQuery = searchQuery,
            heroFilter = HeroFilter.ProWins(FilterOrder.Descending),
            attributeFilter = HeroAttribute.Unknown
        )

        // assert result
        assert(filteredHeroes.isNotEmpty())

        // assert is desc
        for (index in 1 until filteredHeroes.size) {
            val nextHero = filteredHeroes[index-1]
            val winRateNextHero = round(nextHero.proWins.toDouble() / nextHero.proPick.toDouble() * 100).toInt()

            val currentHero = filteredHeroes[index]
            val winRateCurrentHero = round(currentHero.proWins.toDouble() / currentHero.proPick.toDouble() * 100).toInt()

            assert(winRateCurrentHero <= winRateNextHero)
            println("$winRateCurrentHero is smaller or equals than $winRateNextHero")
        }

    }

}

