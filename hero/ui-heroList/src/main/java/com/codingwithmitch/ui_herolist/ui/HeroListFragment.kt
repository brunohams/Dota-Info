package com.codingwithmitch.ui_herolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.ui_herolist.R
import com.codingwithmitch.ui_herolist.ui.adapter.HeroAdapter
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun setupList() {
        view?.findViewById<RecyclerView>(R.id.recycler_view_heroes)?.let { recyclerView ->
            recyclerView.adapter = adapter
        }

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            adapter.setValues(
                heroes = state.filteredHeroes,
            )
        })
    }

    private fun onSelectHero(hero: Hero) {
        view?.let { view ->
            findNavController(view).navigate(
                R.id.action_detail
            )
        }
    }
}