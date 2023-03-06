package online.tinft.collections

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun getCollections(httpClient: HttpClient): List<CollectionResponse> {
    return getCollectionsME(httpClient).map {
        CollectionResponse(
            id = it.collectionSymbol,
            name = it.name,
            image = it.image,
            totalVolume = it.totalVol,
            volume1d = it.vol,
            floorPrice = it.fp,
        )
    }
}

private suspend fun getCollectionsME(httpClient: HttpClient): List<CollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=99",
    )
    return items.body()
}
