package online.tinft.collections

import kotlinx.serialization.Serializable

@Serializable
data class CollectionME(
    val collectionSymbol: String,
    val name: String,
    val image: String? = null,
    val totalVol: Double? = null,
    val vol: Double? = null,
    val fp: Double? = null,
)

@Serializable
data class CollectionResponse(
    val id: String,
    val name: String,
    val image: String?,
    val totalVolume: Double?,
    val volume1d: Double?,
    val floorPrice: Double?,
)
