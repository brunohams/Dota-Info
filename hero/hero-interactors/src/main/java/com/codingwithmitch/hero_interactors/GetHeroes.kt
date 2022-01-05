package com.codingwithmitch.hero_interactors

import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.core.domain.UIComponent
import com.codingwithmitch.hero_datasource.cache.HeroCache
import com.codingwithmitch.hero_datasource.network.HeroService
import com.codingwithmitch.hero_domain.Hero
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroes(
    private val cache: HeroCache,
    private val service: HeroService
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

            // Insert into cache
            cache.insert(heroes)

            // Retrieve from cache
            val cachedHeroes = cache.selectAll()

            // Emit cached data
            emit(DataState.Data<List<Hero>>(cachedHeroes)) // Emit success
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