package com.codingwithmitch.ui_herolist.ui

sealed class HeroListEvents {

    object GetHeroes: HeroListEvents()

    object FilterHeroes: HeroListEvents()

    data class UpdateSearchQuery(
        val searchQuery: String
    ): HeroListEvents()

}
