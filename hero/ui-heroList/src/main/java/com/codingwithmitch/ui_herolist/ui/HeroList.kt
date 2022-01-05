package com.codingwithmitch.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.core.domain.UIComponentState
import com.codingwithmitch.hero_domain.HeroFilter
import com.codingwithmitch.ui_herolist.components.HeroListFilter
import com.codingwithmitch.ui_herolist.components.HeroListItem
import com.codingwithmitch.ui_herolist.components.HeroListToolbar

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            HeroListToolbar(
                heroName = state.searchQuery,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvents.UpdateSearchQuery(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHeroes)
                },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                }
            )
            LazyColumn{
                items(state.filteredHeroes) { hero ->
                    HeroListItem(
                        hero = hero,
                        imageLoader = imageLoader,
                        onSelectHero = {
                            navigateToDetailScreen(hero.id)
                        }
                    )
                }
            }
        }

        if (state.filterDialogState is UIComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                attributeFilter = state.attributeFilter,
                onUpdateHeroFilter = { heroFilter ->
                    events(HeroListEvents.UpdateHeroFilter(heroFilter))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                },
                onUpdateAttributeFilter = { heroAttribute ->
                    events(HeroListEvents.UpdateAttributeFilter(heroAttribute))
                }
            )
        }

        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

