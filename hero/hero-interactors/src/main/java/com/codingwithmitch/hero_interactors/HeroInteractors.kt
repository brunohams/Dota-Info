package com.codingwithmitch.hero_interactors

import com.codingwithmitch.hero_datasource.cache.HeroCache
import com.codingwithmitch.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeroes: GetHeroes,
    val getHero: GetHero,
    val filterHeroes: FilterHeroes,
    val zoomHeroes: ZoomHeroes
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service = service,
                    cache = cache
                ),
                getHero = GetHero(
                    cache = cache
                ),
                filterHeroes = FilterHeroes(),
                zoomHeroes = ZoomHeroes()
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema
        val dbName: String = HeroCache.dbName
    }
}