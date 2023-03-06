package online.tinft.collections

import kotlinx.serialization.Serializable

@Serializable
data class CollectionME(
    val collectionSymbol: String,
    val name: String,
    val image: String?,
    val totalVol: Double?,
    val vol: Double?,
    val fp: Double?,
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
