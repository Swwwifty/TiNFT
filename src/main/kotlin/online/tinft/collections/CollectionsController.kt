package online.tinft.collections

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Calendar

private val cache = mutableListOf<CollectionResponse>()
private var cacheTime: Long = 0

suspend fun getCollections(httpClient: HttpClient): List<CollectionResponse> {
    val curTime = Calendar.getInstance().time.time
    if (cache.isNotEmpty() && curTime - cacheTime < 30 * 60 * 1000) {
        return cache
    }
    cacheTime = curTime
    cache.clear()
    return getCollectionsME(httpClient).map {
        CollectionResponse(
            id = it.collectionSymbol,
            name = it.name,
            image = it.image,
            totalVolume = it.totalVol,
            volume1d = it.vol,
            floorPrice = it.fp,
        )
    }.also {
        cache.addAll(it)
    }
}

private suspend fun getCollectionsME(httpClient: HttpClient): List<CollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=99",
    )
    return items.body()
}
