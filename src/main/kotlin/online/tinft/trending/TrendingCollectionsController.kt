package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import online.tinft.common.format2DigitsLamport
import java.util.Calendar

private val cache = mutableListOf<TrendingCollection>()
private var cacheTime: Long = 0

suspend fun getTrendingCollections(httpClient: HttpClient): List<TrendingCollection> {
    val curTime = Calendar.getInstance().time.time
    if (cache.isNotEmpty() && curTime - cacheTime < 30 * 60 * 1000) {
        return cache
    }
    cacheTime = curTime
    cache.clear()
    return getTrendingCollectionsME(httpClient).map {
        TrendingCollection(
            id = it.collectionSymbol,
            name = it.name,
            image = it.image,
            floorPrice = it.fp?.format2DigitsLamport(),
            likesCount = (it.vol?.div(100))?.toInt() ?: 0,
        )
    }
        .sortedByDescending { it.likesCount }
        .also {
            cache.addAll(it)
        }
}

private suspend fun getTrendingCollectionsME(httpClient: HttpClient): List<TrendingCollectionME> {
    val items = httpClient.get(
        "https://stats-mainnet.magiceden.io/collection_stats/popular_collections/sol?window=1d&limit=30",
    )
    return items.body()
}
