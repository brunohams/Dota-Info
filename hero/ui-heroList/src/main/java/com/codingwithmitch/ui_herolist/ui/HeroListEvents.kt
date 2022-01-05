package com.codingwithmitch.ui_herolist.ui

import com.codingwithmitch.hero_domain.HeroFilter

sealed class HeroListEvents {

    object GetHeroes: HeroListEvents()

    object FilterHeroes: HeroListEvents()

    data class UpdateSearchQuery(
        val searchQuery: String
    ): HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ): HeroListEvents()

}
