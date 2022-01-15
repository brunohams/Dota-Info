package com.codingwithmitch.ui_herolist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.ui_herolist.R

class HeroAdapter(
    private var heroes: List<Hero>,
    private var onSelectHero: (Hero) -> Unit
): RecyclerView.Adapter<HeroAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tv_name)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.hero_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = heroes[position].localizedName
        viewHolder.textView.setOnClickListener {
            onSelectHero(heroes[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = heroes.size

    fun setValues(heroes: List<Hero>) {
        this.heroes = heroes
        notifyDataSetChanged()
    }

}