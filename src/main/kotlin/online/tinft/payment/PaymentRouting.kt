package online.tinft.payment

import io.ktor.client.HttpClient
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.paymentRouting(httpClient: HttpClient) {
    route("/buyItem") {
        get {
            val buyer = call.request.queryParameters["buyer"]!!
            val seller = call.request.queryParameters["seller"]!!
            val auctionHouseAddress = call.request.queryParameters["auctionHouseAddress"]!!
            val tokenMint = call.request.queryParameters["tokenMint"]!!
            val tokenAta = call.request.queryParameters["tokenATA"]!!
            val price = call.request.queryParameters["price"]!!
            call.respond(
                buyItem(
                    httpClient,
                    buyer = buyer,
                    seller = seller,
                    auctionHouseAddress = auctionHouseAddress,
                    tokenMint = tokenMint,
                    tokenAta = tokenAta,
                    price = price,
                ),
            )
        }
    }
}
