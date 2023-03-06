package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun getTrendingItems(httpClient: HttpClient): String {
    return getTrendingItemsByCollectionME(httpClient, "y00ts")
}

private suspend fun getTrendingCollectionsME(httpClient: HttpClient): List<TrendingCollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=1",
    )
    return items.body()
}

private suspend fun getTrendingItemsByCollectionME(httpClient: HttpClient, collectionId: String): String {
    val items = httpClient.get(
        "https://api-mainnet.magiceden.io/idxv2/getListedNftsByCollectionSymbol?collectionSymbol=claynosaurz",
    )
    return items.body()
}
