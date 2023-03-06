package online.tinft.plugins

import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import online.tinft.trending.collectionsRouting

fun Application.configureRouting(httpClient: HttpClient) {
    install(Resources)
    install(AutoHeadResponse)
    routing {
        collectionsRouting(httpClient)
    }
}