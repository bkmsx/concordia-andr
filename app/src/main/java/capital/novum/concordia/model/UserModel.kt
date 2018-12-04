package capital.novum.concordia.model


data class User (
        var id: Int,
        var firstName: String,
        var lastName: String,
        var email: String,
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
class LoginResult : Result() {
    var user: User? = null
}
