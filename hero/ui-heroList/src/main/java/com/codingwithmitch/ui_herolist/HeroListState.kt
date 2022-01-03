package com.codingwithmitch.ui_herolist

import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf()
) {
}