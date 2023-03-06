package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun getTrendingCollections(httpClient: HttpClient): List<TrendingCollection> {
    return getTrendingCollectionsME(httpClient).map {
        TrendingCollection(
            id = it.collectionSymbol,
            name = it.name,
            image = it.image,
            floorPrice = it.fp,
            likesCount = (it.vol?.div(100))?.toInt() ?: 0,
        )
    }.sortedByDescending { it.likesCount }
}

private suspend fun getTrendingCollectionsME(httpClient: HttpClient): List<TrendingCollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=30",
    )
    return items.body()
}
