package capital.novum.concordia.model

/**
 *  Wallet address model
 */
data class  UserWalletAddress (
        var walletId: Int,
        var methodId: Int,
        var address: String
)

data class UserWalletCategory (
        var methodName: String,
        var wallets: List<UserWalletAddress>
)

data class DisplayWalletModel(
        var id: Int,
        var methodId: Int,
        var name: String,
        var type: DisplayType // header, body, footer
)

enum class DisplayType{
    HEADER, BODY, FOOTER
}

class UserWallets : Result() {
    var wallets: List<UserWalletCategory> = listOf()
}