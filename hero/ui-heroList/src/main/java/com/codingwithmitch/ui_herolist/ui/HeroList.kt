package com.codingwithmitch.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.ui_herolist.HeroListState
import com.codingwithmitch.ui_herolist.components.HeroListItem

@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn{
            items(state.heroes) { hero ->
                HeroListItem(
                    hero = hero,
                    imageLoader = imageLoader,
                    onSelectHero = {}
                )
            }
        }
        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

