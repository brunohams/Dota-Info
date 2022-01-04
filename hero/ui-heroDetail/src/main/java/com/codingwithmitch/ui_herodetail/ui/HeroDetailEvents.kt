package com.codingwithmitch.ui_herodetail.ui

import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.hero_domain.Hero

sealed class HeroDetailEvents {

    data class GetHero(
        val id: Int
    ): HeroDetailEvents()
}
