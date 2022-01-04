package com.codingwithmitch.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.core.DataState
import com.codingwithmitch.hero_interactors.GetHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject
constructor(
    private val getHero: GetHero,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailEvents.GetHero(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvents){
        when (event) {
            is HeroDetailEvents.GetHero -> {
                getHero(event.id)
            }
        }
    }

    private fun getHero(id: Int) {
        getHero.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }

                is DataState.Response -> {
                    // TODO Handle error
                }
            }
        }.launchIn(viewModelScope)
    }

}