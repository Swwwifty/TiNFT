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
                price = it.price?.times(0.000000001),
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
        "https://api-mainnet.magiceden.io/idxv2/getListedNftsByCollectionSymbol?collectionSymbol=$collectionId&direction=2&field=1&limit=6&offset=0&mode=all",
    )
    return items.body()
}

private suspend fun getTrendingItemsByCollectionCoralCube(
    httpClient: HttpClient,
    collectionId: String,
): TrendingItemResultCoralCubeResult {
    val items = httpClient.get(
        "https://api.coralcube.io/v1/getItems?offset=0&page_size=5&ranking=price_asc&symbol=$collectionId&taker_address=%22AkvbwvJMxtHPXLDB1fcKS49iLvx2veRqC5eeAySRSfyg%22",
    )
    return items.body()
}
