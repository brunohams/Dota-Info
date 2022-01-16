package com.codingwithmitch.ui_herodetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.ui_herodetail.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment : Fragment() {

    private val viewModel: HeroDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_hero_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStateObserver()
    }

    private fun setupStateObserver() {
        viewModel.state.let { state ->
            state.value.hero?.let { hero -> populateFieldsWith(hero) }
        }
    }

    private fun populateFieldsWith(hero: Hero) {
        view?.findViewById<TextView>(R.id.tv_name)?.text = hero.localizedName
        view?.findViewById<ImageView>(R.id.hero_image)?.load(hero.img) {
            crossfade(750)
            scale(Scale.FILL)
        }
    }

}