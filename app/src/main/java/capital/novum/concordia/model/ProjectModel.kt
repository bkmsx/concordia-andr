package capital.novum.concordia.model

/**
 *  Project Result
 */
data class PaymentMethod (
        val methodId: Int,
        val methodName: String,
        val pricePerToken: String,
        val methodType: String,
        val walletAddress: String,
        val accountName: String,
        val holderAddress: String,
        val accountNumber: String,
        val swiftCode: String,
        val bankName: String,
        val bankAddress: String
)

data class SalePeriod (
        var title: String,
        var saleStart: String,
        var saleEnd: String,
        var discount: String
)

data class Project (
        var projectId: Int,
        var logo: String,
        var title: String,
        var addedDate: String,
        var shortDescription: String,
        var detailedDescription: String,
        var userParticipated: String,
        var currentDiscount: String,
        var currentSaleStart: String,
        var currentSaleEnd: String,
        var currentTier: String,
        var minimumToken: String,
        var termsUrl: String,
        var websiteUrl: String,
        var paymentMethods: List<PaymentMethod>,
        var status: String,
        var salePeriods: List<SalePeriod>
)

class ProjectList : Result() {
    var projects: List<Project> = listOf()
}

class ProjectDetail : Result() {
    var project: Project? = null
}