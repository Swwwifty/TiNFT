package online.tinft.collections

import io.ktor.client.HttpClient
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.collectionsRouting(httpClient: HttpClient) {
    route("/collections") {
        get {
            call.respond(getCollections(httpClient))
        }
    }
}
