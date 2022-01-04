package com.codingwithmitch.ui_herodetail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HeroDetail(
    state: HeroDetailState
) {
    state.hero?.let { hero ->
        Text("Hero ID = ${hero.localizedName}")
    } ?: Text("Null Hero")
}