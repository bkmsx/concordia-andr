package capital.novum.concordia.model

data class ReferralBonus(
        val userName: String,
        val amount: String,
        val tier: Int
)

class ReferralListResult : Result() {
    var referralList: List<ReferralBonus> = listOf()
}