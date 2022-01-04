package com.codingwithmitch.ui_herodetail

import androidx.lifecycle.ViewModel
import com.codingwithmitch.hero_interactors.GetHero
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject
constructor(
    private val getHero: GetHero
): ViewModel() {

}