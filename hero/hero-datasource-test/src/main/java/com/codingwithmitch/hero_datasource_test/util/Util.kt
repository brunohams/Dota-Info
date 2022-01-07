package com.codingwithmitch.hero_datasource_test.util

import com.codingwithmitch.hero_datasource.network.HeroDto
import com.codingwithmitch.hero_datasource.network.toHero
import com.codingwithmitch.hero_domain.Hero
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeHeroData(jsonData: String): List<Hero> {
    return json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
}