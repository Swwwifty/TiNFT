package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun getTrendingItems(httpClient: HttpClient): List<TrendingItem> {
    return getTrendingCollectionsME(httpClient)
        .flatMap {
            getTrendingItemsByCollectionME(httpClient, it.collectionSymbol).results
        }
        .map {
            TrendingItem(
                id = it.mintAddress,
                name = it.title,
                image = it.img,
                price = it.price,
                likesCount = (10..100).random(),
            )
        }
        .sortedByDescending { it.likesCount }
}

private suspend fun getTrendingCollectionsME(httpClient: HttpClient): List<TrendingCollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=5",
    )
    return items.body()
}

private suspend fun getTrendingItemsByCollectionME(httpClient: HttpClient, collectionId: String): TrendingItemResultME {
    val items = httpClient.get(
        "https://api-mainnet.magiceden.io/rpc/getListedNFTsByQueryLite?q={\"\$match%22:{\"collectionSymbol\":\"$collectionId\"}}",
    )
    return items.body()
}
