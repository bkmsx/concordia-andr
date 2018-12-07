package capital.novum.concordia.model

data class PromotionModel(
        val id: Int,
        val projectId: Int,
        val title: String,
        val subTitle: String,
        val bannerImage: String,
        val caption: String,
        val shortDescription: String,
        val detailedDescription: String,
        val exampleDescription: String,
        val freeTokensPerShare: Int,
        val createdAt: String,
        val updatedAt: String,
        val defaultBanner: Int
)

class PromotionResult : Result() {
    var promotion: PromotionModel? = null
}