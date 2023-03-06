package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.collectionsRouting(httpClient: HttpClient) {
    route("/collections") {
        get {
            val items = getCollectionsME(httpClient)
            call.respond(items)
        }
    }
}

private suspend fun getCollectionsME(httpClient: HttpClient): List<TrendingMEItem> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=99"
    )
    return items.body()
}