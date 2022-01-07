package com.codingwithmitch.hero_datasource_test.network

import com.codingwithmitch.hero_datasource.network.HeroService
import com.codingwithmitch.hero_datasource.network.HeroServiceImpl
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataEmpty
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataMalformed
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataValid
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class HeroServiceFake {

    private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

    fun build(
        type: HeroServiceResponseType
    ): HeroService {
        val client = HttpClient(MockEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true // Ignore extra fields if server returns
                    }
                )
            }
            engine {
                addHandler { request ->
                    when (request.url.fullUrl) {
                        "https://api.opendota.com/api/heroStats" -> {
                            val responseHeaders = headersOf(
                                "Content-Type" to listOf("application/json", "charset=utf-8")
                            )
                            when (type) {
                                is HeroServiceResponseType.EmptyList -> {
                                    respond(
                                        HeroDataEmpty.data,
                                        status = HttpStatusCode.OK,
                                        headers = responseHeaders
                                    )
                                }
                                is HeroServiceResponseType.Malformed -> {
                                    respond(
                                        HeroDataMalformed.data,
                                        status = HttpStatusCode.OK,
                                        headers = responseHeaders
                                    )
                                }
                                is HeroServiceResponseType.GoodData -> {
                                    respond(
                                        HeroDataValid.data,
                                        status = HttpStatusCode.OK,
                                        headers = responseHeaders
                                    )
                                }
                                is HeroServiceResponseType.Http404 -> {
                                    respond(
                                        HeroDataEmpty.data,
                                        status = HttpStatusCode.NotFound,
                                        headers = responseHeaders
                                    )
                                }
                            }
                        }
                        else -> error("Unhandled ${request.url.fullUrl}")
                    }
                }
            }
        }
        return HeroServiceImpl(client)
    }
}