package com.codingwithmitch.hero_datasource.network

import com.codingwithmitch.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.request.*
import kotlin.text.get

class HeroServiceImpl(
    private val httpClient: HttpClient
): HeroService {
    override suspend fun getHeroStats(): List<Hero> {
        return httpClient.get<List<HeroDto>>{
            url(Endpoints.HERO_STATS)
        }.map { it.toHero() }
    }
}