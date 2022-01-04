package com.codingwithmitch.ui_herodetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HeroDetail(
    heroId: Int?
) {
    Text("Hero ID = $heroId")
}