package online.tinft.trending

import kotlinx.serialization.Serializable

@Serializable
data class TrendingCollectionME(
    val collectionSymbol: String,
    val name: String,
    val image: String?,
    val fp: Double?,
    val vol: Double?,
)

@Serializable
data class TrendingCollection(
    val id: String,
    val name: String,
    val image: String?,
    val floorPrice: Double?,
    val likesCount: Int,
)
