package online.tinft.items

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import online.tinft.common.format2DigitsLamport
import online.tinft.trending.TrendingCollectionME
import java.util.Calendar

private val itemsCache = mutableListOf<Item>()
private var cacheTime: Long = 0

suspend fun getItems(httpClient: HttpClient): List<Item> {
    val curTime = Calendar.getInstance().time.time
    if (itemsCache.isNotEmpty() && curTime - cacheTime < 30 * 60 * 1000) {
        return itemsCache
    }
    cacheTime = curTime
    itemsCache.clear()
    return getCollectionsME(httpClient)
        .flatMap { collection ->
            getItemsByCollectionCoralCube(httpClient, collection.collectionSymbol).items.map {
                Item(
                    id = it.mint,
                    name = it.name,
                    image = it.image,
                    price = it.price?.format2DigitsLamport(),
                    likesCount = (10..100).random(),
                    collectionId = collection.collectionSymbol,
                    collectionName = collection.name,
                    collectionImage = collection.image,
                )
            }
        }
        .shuffled()
        .also {
            itemsCache.addAll(it)
        }
}

private suspend fun getCollectionsME(httpClient: HttpClient): List<TrendingCollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=15",
    )
    return items.body()
}

private suspend fun getItemsByCollectionME(httpClient: HttpClient, collectionId: String): ItemResultME {
    val items = httpClient.get(
        "https://api-mainnet.magiceden.io/idxv2/getListedNftsByCollectionSymbol?collectionSymbol=$collectionId&direction=2&field=1&limit=2&offset=0&mode=all",
    )
    return items.body()
}

private suspend fun getItemsByCollectionCoralCube(
    httpClient: HttpClient,
    collectionId: String,
): ItemResultCoralCubeResult {
    val items = httpClient.get(
        "https://api.coralcube.io/v1/getItems?offset=0&page_size=4&ranking=price_asc&symbol=$collectionId&taker_address=%22AkvbwvJMxtHPXLDB1fcKS49iLvx2veRqC5eeAySRSfyg%22",
    )
    return items.body()
}
