package online.tinft.trending

import kotlinx.serialization.Serializable

@Serializable
data class TrendingItemResultME(
    val results: List<TrendingItemME>,
)

@Serializable
data class TrendingItemME(
    val mintAddress: String,
    val title: String,
    val img: String?,
    val price: Double?,
)

@Serializable
data class TrendingItem(
    val id: String,
    val name: String,
    val image: String?,
    val price: Double?,
    val likesCount: Int,
)