package capital.novum.concordia.model

/**
 *  Participate history
 */

data class PaymentDestination(
        val methodName: String,
        val walletAddress: String,
        val accountName: String,
        val holderAddress: String,
        val accountNumber: String,
        val swiftCode: String,
        val bankName: String,
        val bankAddress: String
)

data class ParticipateHistory(
        val logo: String,
        val title: String,
        val projectId: Int,
        val historyId: Int,
        val addedDate: String,
        val tokensPurchased: String,
        val paymentMode: String,
        val discount: String,
        val paymentStatus: String,
        val paymentDestination: PaymentDestination,
        val paymentSource: String,
        val amount: String,
        val promotion: String,
        val pricePerToken: String
)

class ParticipateListResult : Result() {
    val history: List<ParticipateHistory> = listOf()
}

class ParticipateDetailResult : Result() {
    var historyDetail: ParticipateHistory? = null
}