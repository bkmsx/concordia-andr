package capital.novum.concordia.model

data class Result (var code: Int, var message: String)

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

