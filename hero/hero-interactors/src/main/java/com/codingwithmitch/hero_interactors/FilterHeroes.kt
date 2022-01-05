package com.codingwithmitch.hero_interactors

import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.core.domain.FilterOrder
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.hero_domain.HeroAttribute
import com.codingwithmitch.hero_domain.HeroFilter
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import kotlin.math.round

class FilterHeroes {

    fun execute(
        current: List<Hero>,
        searchQuery: String,
        heroFilter: HeroFilter,
        attributeFilter: HeroAttribute
    ): List<Hero> {
        try {
            var filteredList: MutableList<Hero> = current.filter {
                it.localizedName.lowercase().contains(searchQuery.lowercase())
            }.toMutableList()

            when (heroFilter) {
                is HeroFilter.Alphabetic -> {
                    when (heroFilter.order) {
                        FilterOrder.Ascending -> {
                            filteredList.sortBy { it.localizedName }
                        }
                        FilterOrder.Descending -> {
                            filteredList.sortByDescending { it.localizedName }
                        }
                    }
                }
                is HeroFilter.ProWins -> {
                    when (heroFilter.order) {
                        FilterOrder.Ascending -> {
                            filteredList.sortBy {
                                getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                            }
                        }
                        FilterOrder.Descending -> {
                            filteredList.sortByDescending {
                                getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                            }
                        }
                    }
                }
            }

            when (attributeFilter) {
                is HeroAttribute.Strength -> {
                    filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Strength }.toMutableList()
                }
                HeroAttribute.Agility -> {
                    filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Agility }.toMutableList()
                }
                HeroAttribute.Intelligence -> {
                    filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Intelligence }.toMutableList()
                }
                HeroAttribute.Unknown -> {}
            }

            return filteredList

        } catch (e: Exception) {
            e.printStackTrace()
            return current
        }
    }

    private fun getWinRate(proWins: Double, proPick: Double): Int {
        return if (proPick <= 0) {
            0
        } else {
            val winRate: Int = round(proWins / proPick * 100).toInt()
            winRate
        }
    }

}