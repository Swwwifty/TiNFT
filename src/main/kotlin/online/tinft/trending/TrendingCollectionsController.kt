package online.tinft.trending

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Calendar
import kotlin.math.floor

private val cache = mutableListOf<TrendingCollection>()
private var cacheTime: Long = 0
private const val CURRENCY_RATE = 1_000_000_000

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
            floorPrice = it.fp?.let { price -> floor(price / CURRENCY_RATE / 100) * 100 },
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
