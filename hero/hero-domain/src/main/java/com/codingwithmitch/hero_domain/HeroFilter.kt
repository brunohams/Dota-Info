package com.codingwithmitch.hero_domain

import com.codingwithmitch.core.domain.FilterOrder

sealed class HeroFilter(
    val uiValue: String
) {

    data class Alphabetic(
        val order: FilterOrder = FilterOrder.Descending
    ): HeroFilter("Hero")

    data class ProWins(
        val order: FilterOrder = FilterOrder.Descending
    ): HeroFilter("Pro Wins")

}