package com.codingwithmitch.ui_herodetail.ui

sealed class HeroDetailEvents {

    data class GetHero(
        val id: Int
    ): HeroDetailEvents()
}
