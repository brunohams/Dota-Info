package com.codingwithmitch.ui_herolist.ui

import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.core.domain.UIComponentState
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.hero_domain.HeroAttribute
import com.codingwithmitch.hero_domain.HeroFilter

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf(),
    val filteredHeroes: List<Hero> = listOf(),
    val searchQuery: String = "",
    val heroFilter: HeroFilter = HeroFilter.Alphabetic(),
    val attributeFilter: HeroAttribute = HeroAttribute.Unknown,
    val filterDialogState: UIComponentState = UIComponentState.Hide
) {
}