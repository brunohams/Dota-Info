package com.codingwithmitch.ui_herolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.ui_herolist.R
import com.codingwithmitch.ui_herolist.ui.adapter.HeroAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.content.res.Configuration
import androidx.recyclerview.widget.GridLayoutManager
import com.codingwithmitch.hero_domain.Zoom
import com.google.android.material.floatingactionbutton.FloatingActionButton

@AndroidEntryPoint
class HeroListFragment : Fragment() {

    private val viewModel: HeroListViewModel by viewModels()
    private var adapter: HeroAdapter = HeroAdapter(listOf(), this::onSelectHero)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_hero_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeState()
        setupFabButtons()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            updateAdapterValues(state.filteredHeroes)
            setSpanCount(state.spanCount)
        })
    }

    private fun setSpanCount(spanCount: Int) {
        view?.findViewById<RecyclerView>(R.id.recycler_view_heroes)?.let { recyclerView ->
            recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        }
    }

    private fun setupFabButtons() {
        val fabMap = mapOf(
            R.id.fab_zoom_in to Zoom.In,
            R.id.fab_zoom_out to Zoom.Out
        )

        for ((id, zoom) in fabMap) {
            view?.findViewById<FloatingActionButton>(id)?.let { fab_zoom ->
                fab_zoom.setOnClickListener {
                    triggerZoomEvent(zoom)
                }
            }
        }

    }

    private fun triggerZoomEvent(zoom: Zoom) {
        viewModel.state.value?.let { state ->
            viewModel.onTriggerEvent(
                event = HeroListEvents.ZoomGrid(
                    spanCount = state.spanCount,
                    zoom = zoom
                )
            )
        }
    }

    private fun setupList() {
        view?.findViewById<RecyclerView>(R.id.recycler_view_heroes)?.let { recyclerView ->
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(context, getListSpanCount())
        }
    }
    
    private fun updateAdapterValues(heroes: List<Hero>) {
        adapter.setValues(
            heroes = heroes,
        )
    }

    private fun getListSpanCount(): Int {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                4
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                2
            }
            Configuration.ORIENTATION_SQUARE -> {
                1
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                1
            }
            else -> {
                2
            }
        }
    }

    private fun onSelectHero(hero: Hero) {
        view?.let { view ->
            val bundle = bundleOf("heroId" to hero.id)
            findNavController(view).navigate(R.id.action_detail, bundle)
        }
    }
}