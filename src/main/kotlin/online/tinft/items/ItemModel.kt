package online.tinft.items

import kotlinx.serialization.Serializable

@Serializable
data class ItemResultME(
    val results: List<ItemME>,
)

@Serializable
data class ItemME(
    val mintAddress: String,
    val title: String,
    val img: String? = null,
    val price: Double? = null,
)

@Serializable
data class Item(
    val id: String,
    val name: String,
    val image: String?,
    val price: Double?,
    val likesCount: Int,
    val collectionId: String,
    val collectionName: String,
    val collectionImage: String?,
)

@Serializable
data class ItemResultCoralCubeResult(
    val items: List<ItemResultCoralCubeItem>,
)

@Serializable
data class ItemResultCoralCubeItem(
    val name: String,
    val mint: String,
    val image: String?,
    val price: Double?,
)
