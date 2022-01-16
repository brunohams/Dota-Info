package com.codingwithmitch.hero_interactors

import com.codingwithmitch.hero_domain.Zoom

class ZoomHeroes {

    fun execute(spanCount: Int, zoom: Zoom): Int {
        var count = spanCount
        when (zoom) {
            Zoom.In -> {
                if (count > 1) count--
            }
            Zoom.Out -> {
                count++
            }
        }
        return count
    }

}