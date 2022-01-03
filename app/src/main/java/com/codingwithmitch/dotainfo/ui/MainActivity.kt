package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import coil.ImageLoader
import com.codingwithmitch.core.DataState
import com.codingwithmitch.core.Logger
import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.core.UIComponent
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.codingwithmitch.hero_interactors.HeroInteractors
import com.codingwithmitch.ui_herolist.ui.HeroList
import com.codingwithmitch.ui_herolist.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private val progressBarState: MutableState<ProgressBarState> = mutableStateOf(ProgressBarState.Idle)
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(.25)
            .crossfade(true)
            .build()

        val getHeroes = HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.dbName
            )
        ).getHeroes
        val logger = Logger("GetHeroesTest")
        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data?: listOf())
                }
                is DataState.Loading -> {
                    progressBarState.value = dataState.progressBarState
                }
            }
        }.launchIn(CoroutineScope(IO))

        setContent {
            DotaInfoTheme {
                HeroList(
                    state = state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}















