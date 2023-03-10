package online.tinft.payment

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun buyItem(
    httpClient: HttpClient,
    buyer: String,
    seller: String,
    auctionHouseAddress: String,
    tokenMint: String,
    tokenAta: String,
    price: String,
): String {
    return httpClient.get(
        "https://api-mainnet.magiceden.dev/v2/instructions/buy_now",
    ) {
        url {
            parameters.append("buyer", buyer)
            parameters.append("seller", seller)
            parameters.append("auctionHouseAddress", auctionHouseAddress)
            parameters.append("tokenMint", tokenMint)
            parameters.append("tokenATA", tokenAta)
            parameters.append("price", price)
            parameters.append("sellerExpiry", "-1")
            parameters.append("buyerCreatorRoyaltyPercent", "0")
        }
    }.body()
}
