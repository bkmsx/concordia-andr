package capital.novum.concordia.model

import android.content.Context
import android.preference.PreferenceManager

object LocalData {

    fun saveUserDetail(context : Context, user: User) {
        val sharePref = PreferenceManager.getDefaultSharedPreferences(context)
        sharePref.edit()
                .putInt(UserConstant.id, user.id)
                .putString(UserConstant.firstName, user.firstName)
                .apply()
    }
}

object UserConstant {
    val id = "id"
    val firstName = "firstName"
    val lastName = "lastName"
    val dateOfBirth = "dateOfBirth"
    val phoneNumber = "phoneNumber"
    val countryCode = "countryCode"
    val countryOfResidence = "countryOfResidence"
    val deviceSecurityEnable = "deviceSecurityEnable"
    val deviceId = "deviceId"
    val typeOfSecurity = "typeOfSecurity"
    val securityToken = "securityToken"
    val status = "status"
    val citizenshipId = "citizenshipId"
    val citizenship = "citizenship"
    val passportNumber = "passportNumber"
    val passportPhoto = "passportPhoto"
    val selfiePhoto = "selfiePhoto"
    val erc20Address = "erc20Address"
    val kycStatus = "kycStatus"
    val referralCode = "referralCode"
    val referredBy = "referredBy"
    val passportVerified = "passportVerified"
    val token = "token"
    val createdAt = "createdAt"
    val updatedAt = "updatedAt"
}