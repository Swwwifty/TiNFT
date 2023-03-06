package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.trendingRouting(httpClient: HttpClient) {
    route("/trending/collections") {
        get {
            call.respond(getTrendingCollections(httpClient))
        }
    }
    route("/trending/items") {
        get {
            call.respond(getTrendingItems(httpClient))
        }
    }
}
