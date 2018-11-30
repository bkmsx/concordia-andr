package capital.novum.concordia.model

data class AppPaymentMethod (
        val id: Int,
        val name: String,
        val type: String,
        val createdAt: String,
        val updatedAt: String
)

class PaymentMethodListResult : Result() {
    var paymentMethods: List<AppPaymentMethod>? = listOf()
}