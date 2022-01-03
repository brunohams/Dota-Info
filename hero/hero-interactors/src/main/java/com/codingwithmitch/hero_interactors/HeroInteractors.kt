package com.codingwithmitch.hero_interactors

import com.codingwithmitch.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeroes: GetHeroes,
    // TODO ADD MORE
) {
    companion object Factory {
        fun build(): HeroInteractors{
            val service = HeroService.build()
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service
                )
            )
        }
    }
}