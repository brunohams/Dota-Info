package com.codingwithmitch.ui_herolist.ui

import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf(),
    val filteredHeroes: List<Hero> = listOf(),
    val searchQuery: String = ""
) {
}