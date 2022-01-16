package com.codingwithmitch.hero_interactors

import com.codingwithmitch.hero_domain.Zoom
import org.junit.Test

class ZoomHeroesGridTest {

    private lateinit var zoomHeroes: ZoomHeroes

    @Test
    fun `when zoom in, should return less one`() {
        val initialSpanCount = 2

        // prepare use case
        zoomHeroes = ZoomHeroes()

        val zoomedInSpanCount: Int = zoomHeroes.execute(
            spanCount = initialSpanCount,
            zoom = Zoom.In
        )

        assert(initialSpanCount > zoomedInSpanCount)
        println("initialSpanCount: $initialSpanCount")
        println("zoomedInSpanCount: $zoomedInSpanCount")
    }

    @Test
    fun `when zoom in, should NOT return zero`() {
        val initialSpanCount = 1

        // prepare use case
        zoomHeroes = ZoomHeroes()

        val zoomedInSpanCount: Int = zoomHeroes.execute(
            spanCount = initialSpanCount,
            zoom = Zoom.In
        )

        assert(zoomedInSpanCount > 0)
        println("zoomedInSpanCount: $zoomedInSpanCount")
    }

    @Test
    fun `when zoom out, should return more one`() {
        val initialSpanCount = 2

        // prepare use case
        zoomHeroes = ZoomHeroes()

        val zoomedInSpanCount: Int = zoomHeroes.execute(
            spanCount = initialSpanCount,
            zoom = Zoom.Out
        )

        assert(initialSpanCount < zoomedInSpanCount)
        println("initialSpanCount: $initialSpanCount")
        println("zoomedInSpanCount: $zoomedInSpanCount")
    }

}