package capital.novum.concordia.model

import android.content.Context
import android.preference.PreferenceManager

object LocalData {
    fun saveUserDetail(context : Context, user: User) {
        val sharePref = PreferenceManager.getDefaultSharedPreferences(context)
        sharePref.edit()
                .putInt(UserConstant.id, user.id)
                .putString(UserConstant.firstName, user.firstName)
                .putString(UserConstant.lastName, user.lastName)
                .putString(UserConstant.email, user.email)
                .putString(UserConstant.dateOfBirth, user.dateOfBirth)
                .putString(UserConstant.phoneNumber, user.phoneNumber)
                .putString(UserConstant.countryCode, user.countryCode)
                .putString(UserConstant.countryOfResidence, user.countryOfResidence)
                .putString(UserConstant.deviceSecurityEnable, user.deviceSecurityEnable)
                .putString(UserConstant.deviceId, user.deviceId)
                .putString(UserConstant.typeOfSecurity, user.typeOfSecurity)
                .putString(UserConstant.securityToken, user.securityToken)
                .putString(UserConstant.status, user.status)
                .putString(UserConstant.citizenshipId, user.citizenshipId)
                .putString(UserConstant.citizenship, user.citizenship)
                .putString(UserConstant.passportNumber, user.passportNumber)
                .putString(UserConstant.passportPhoto, user.passportPhoto)
                .putString(UserConstant.selfiePhoto, user.selfiePhoto)
                .putString(UserConstant.erc20Address, user.erc20Address)
                .putString(UserConstant.kycStatus, user.kycStatus)
                .putString(UserConstant.referralCode, user.referralCode)
                .putString(UserConstant.referredBy, user.referredBy)
                .putString(UserConstant.passportVerified, user.passportVerified)
                .putString(UserConstant.createdAt, user.createdAt)
                .putString(UserConstant.updatedAt, user.updatedAt)
                .putString(UserConstant.token, user.token)
                .apply()
    }

    fun removeUserDetail(context : Context) {
        val sharePref = PreferenceManager.getDefaultSharedPreferences(context)
        sharePref.edit()
                .remove(UserConstant.id)
                .remove(UserConstant.firstName)
                .remove(UserConstant.lastName)
                .remove(UserConstant.email)
                .remove(UserConstant.dateOfBirth)
                .remove(UserConstant.phoneNumber)
                .remove(UserConstant.countryCode)
                .remove(UserConstant.countryOfResidence)
                .remove(UserConstant.deviceSecurityEnable)
                .remove(UserConstant.deviceId)
                .remove(UserConstant.typeOfSecurity)
                .remove(UserConstant.securityToken)
                .remove(UserConstant.status)
                .remove(UserConstant.citizenshipId)
                .remove(UserConstant.citizenship)
                .remove(UserConstant.passportNumber)
                .remove(UserConstant.passportPhoto)
                .remove(UserConstant.selfiePhoto)
                .remove(UserConstant.erc20Address)
                .remove(UserConstant.kycStatus)
                .remove(UserConstant.referralCode)
                .remove(UserConstant.referredBy)
                .remove(UserConstant.passportVerified)
                .remove(UserConstant.createdAt)
                .remove(UserConstant.updatedAt)
                .remove(UserConstant.token)
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
    val email = "email"
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