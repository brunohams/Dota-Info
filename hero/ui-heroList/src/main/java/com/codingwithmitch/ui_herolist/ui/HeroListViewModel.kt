package com.codingwithmitch.ui_herolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.core.domain.DataState
import com.codingwithmitch.core.domain.Queue
import com.codingwithmitch.core.domain.UIComponent
import com.codingwithmitch.core.util.Logger
import com.codingwithmitch.hero_domain.HeroAttribute
import com.codingwithmitch.hero_domain.HeroFilter
import com.codingwithmitch.hero_interactors.FilterHeroes
import com.codingwithmitch.hero_interactors.GetHeroes
import com.codingwithmitch.hero_domain.Zoom
import com.codingwithmitch.hero_interactors.ZoomHeroes
import com.codingwithmitch.util.SafeMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel
@Inject
constructor(
    private val getHeroes: GetHeroes,
    private val filterHeroes: FilterHeroes,
    private val zoomHeroes: ZoomHeroes,
    @Named("heroListLogger") private val logger: Logger
): ViewModel() {

    private val _state: SafeMutableLiveData<HeroListState> = SafeMutableLiveData(HeroListState())
    val state: LiveData<HeroListState> = _state

    init {
        onTriggerEvent(HeroListEvents.GetHeroes)
    }

    fun onTriggerEvent(event: HeroListEvents) {
        when (event) {
            is HeroListEvents.GetHeroes -> {
                getHeroes()
            }
            HeroListEvents.FilterHeroes -> {
                filterHeroes()
            }
            is HeroListEvents.UpdateSearchQuery -> {
                updateSearchQuery(event.searchQuery)
            }
            is HeroListEvents.UpdateHeroFilter -> {
                updateHeroFilter(event.heroFilter)
            }
            is HeroListEvents.UpdateFilterDialogState -> {
                _state.value = _state.value.copy(filterDialogState = event.uiComponentState)
            }
            is HeroListEvents.UpdateAttributeFilter -> {
                updateAttributeFilter(event.attribute)
            }
            HeroListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
            is HeroListEvents.ZoomGrid -> {
                zoomGrid(event.spanCount, event.zoom)
            }
        }
    }

    private fun zoomGrid(spanCount: Int, zoom: Zoom) {
        val zoomedSpanCount = zoomHeroes.execute(
            spanCount = spanCount,
            zoom = zoom
        )
        _state.value = _state.value.copy(spanCount = zoomedSpanCount)
    }

    private fun getHeroes() {
        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    _state.value = _state.value.copy(heroes = dataState.data?: listOf())
                    filterHeroes()
                }
                is DataState.Loading -> {
                    _state.value = _state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateSearchQuery(searchQuery: String) {
        _state.value = _state.value.copy(searchQuery = searchQuery)
    }

    private fun filterHeroes() {
        val filteredList = filterHeroes.execute(
            current = _state.value.heroes,
            searchQuery = _state.value.searchQuery,
            heroFilter = _state.value.heroFilter,
            attributeFilter = _state.value.attributeFilter
        )
        _state.value = _state.value.copy(filteredHeroes = filteredList)
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        _state.value = _state.value.copy(heroFilter = heroFilter)
        filterHeroes()
    }

    private fun updateAttributeFilter(attribute: HeroAttribute) {
        _state.value = _state.value.copy(attributeFilter = attribute)
        filterHeroes()
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = _state.value.errorQueue
        queue.add(uiComponent)
        _state.value = _state.value.copy(errorQueue = Queue(mutableListOf())) // WORKAROUND - force to reCompose
        _state.value = _state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = _state.value.errorQueue
            queue.remove()
            _state.value = _state.value.copy(errorQueue = Queue(mutableListOf())) // WORKAROUND - force to reCompose
            _state.value = _state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from DialogQueue")
        }
    }

}