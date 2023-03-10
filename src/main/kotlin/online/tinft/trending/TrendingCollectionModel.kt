package online.tinft.trending

import kotlinx.serialization.Serializable

@Serializable
data class TrendingCollectionME(
    val collectionSymbol: String,
    val name: String,
    val image: String? = null,
    val fp: Double? = null,
    val vol: Double? = null,
)

@Serializable
data class TrendingCollection(
    val id: String,
    val name: String,
    val image: String?,
    val floorPrice: Double?,
    val likesCount: Int,
)
