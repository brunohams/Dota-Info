package com.codingwithmitch.hero_interactors

import com.codingwithmitch.core.DataState
import com.codingwithmitch.core.Logger
import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.core.UIComponent
import com.codingwithmitch.hero_datasource.network.HeroService
import com.codingwithmitch.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroes(
    private val service: HeroService,
    // TODO (ADD CACHING)
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading)) // Start Loading

            val heroes: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )
                listOf()
            }

            // TODO CACHING

            emit(DataState.Data<List<Hero>>(heroes)) // Emit success
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle)) // End Loading
        }
    }
}