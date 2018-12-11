package capital.novum.concordia.setting

import android.app.Activity
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.LoginResult
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.registration.VerifyOTPActivity
import capital.novum.concordia.util.Constants
import capital.novum.concordia.util.FingerprintUtil
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.setting_mobile_phone_activity.*

class ChangeMobilePhoneActivity : BaseActivity() {
    lateinit var countryCode: String
    lateinit var phoneNumber: String
    val fingerprintUtil by lazy { FingerprintUtil(this) }
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_mobile_phone_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE MOBILE NUMBER"
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener {
            countryCode = countryCodePicker.selectedCountryCode
            phoneNumber = phoneNumberEdt.text.toString()
            val currentTime = System.currentTimeMillis()
            val otpTimeStamp = PreferenceManager.getDefaultSharedPreferences(this).getLong("otpTimeStamp", 0)
            if (currentTime - otpTimeStamp < 60000L) {
                goNext()
                return@setOnClickListener
            }
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
            val oldCountryCode = sharedPreference.getString(UserConstant.countryCode, "")
            val oldPhoneNumber = sharedPreference.getString(UserConstant.phoneNumber, "")
            if (phoneNumber == "") {
                Utils.showNoticeDialog(this, msg = "Please input phone number")
                return@setOnClickListener
            }
            if (countryCode == oldCountryCode && phoneNumber == oldPhoneNumber) {
                Utils.showNoticeDialog(this, msg = "New phone number must be different")
                return@setOnClickListener
            }

            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putLong("otpTimeStamp", currentTime)
                    .apply()
            authenticate()
        }
    }

    /**
     *  Call API
     */
    private fun sendOTP() {
        requestHttp(UrlConstant.SEND_OTP, hashMapOf(
                "country_code" to countryCode,
                "phone_number" to phoneNumber,
                "via" to "sms"
        )) {
            goNext()
        }
    }

    private fun loginAccount(params: HashMap<String, String>) {
        requestHttp(UrlConstant.LOGIN_ACCOUNT, params) {
            val result = it as LoginResult
            LocalData.saveUserDetail(this, result.user!!)
            sendOTP()
        }
    }

    /**
     *  Events
     */
    private fun authenticate() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val securityEnable = sharedPreferences.getString(UserConstant.deviceSecurityEnable, "false")
        val email = sharedPreferences.getString(UserConstant.email, "")
        if (securityEnable != "false" && fingerprintUtil.canUseFingerprint()) {
            fingerprintUtil.startAuth(title = "Authenticate Your Fingerprint", msg = ""){
                sendOTP()
            }
        } else {
            Utils.showInputPasswordDialog(this) {
                val deviceId = sharedPreferences.getString(Constants.DEVICE_ID, "")
                loginAccount(hashMapOf(
                        "email" to email,
                        "password" to it,
                        "device_id" to deviceId,
                        "platform" to "Android"
                ))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Utils.showNoticeDialog(this, msg = "Update successfully!!") {
                finish()
            }
        }
    }

    /**
     *  Navigations
     */
    fun goNext() {
        val intent = Intent(this, VerifyOTPActivity::class.java)
        intent.putExtra("countryCode", countryCode)
        intent.putExtra("phoneNumber", phoneNumber)
        startActivityForResult(intent, 1)
    }
}