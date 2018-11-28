package capital.novum.concordia.model

data class Result (val code: Int, val message: String)

data class Nationality(
        var citizenships: List<Citizenship>,
        var countries: List<Country>,
        var code: Int,
        var message: String)
data class Country(val countryId: Int, val country: String)
data class Citizenship(val nationalityId: Int, val nationality: String)

data class User (
        var id: Int,
        var firstName: String,
        var lastName: String,
        var dateOfBirth: String,
        var phoneNumber: String,
        var countryCode: String,
        var countryOfResidence: String,
        var deviceSecurityEnable: String,
        var deviceId: String,
        var typeOfSecurity: String,
        var securityToken: String,
        var status: String,
        var citizenshipId: String,
        var citizenship: String,
        var passportNumber: String,
        var passportPhoto: String,
        var selfiePhoto: String,
        var erc20Address: String,
        var kycStatus: String,
        var referralCode: String,
        var referredBy: String,
        var passportVerified: String,
        var token: String,
        var createdAt: String,
        var updatedAt: String
)
data class LoginResult(
        var code: Int,
        var message: String,
        var user: User
)

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

data class ProjectList(
        var projects: List<Project>,
        var message: String,
        var code: Int
)

data class ProjectDetail (
        var project: Project,
        var message: String,
        var code: Int
)

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

data class UserWallets (
        var wallets: List<UserWalletCategory>,
        var code: Int,
        var message: String
)

/**
 *  Coin Market
 */

data class Money (val price: Double)

data class Quote (
        val BTC: Money,
        val XLM: Money,
        val USD: Money
)

data class ETH (val quote: Quote)

data class Data (val ETH: ETH)

data class Status(val errorCode: Int, val errorMessage: String)

data class CoinMarketResult(val data: Data, val status: Status)