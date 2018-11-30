package capital.novum.concordia.setting

import android.app.Activity
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.registration.VerifyOTPActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.setting_mobile_phone_activity.*

class ChangeMobilePhoneActivity : BaseActivity() {
    lateinit var countryCode: String
    lateinit var phoneNumber: String
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
        btnNext.setOnClickListener { sendOTP() }
    }

    /**
     *  Call API
     */
    private fun sendOTP() {
        countryCode = countryCodePicker.selectedCountryCode
        phoneNumber = phoneNumberEdt.text.toString()
        val currentTime = System.currentTimeMillis()
        val otpTimeStamp = PreferenceManager.getDefaultSharedPreferences(this).getLong("otpTimeStamp", 0)
        if (currentTime - otpTimeStamp < 60000L) {
            goNext()
            return
        }
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val oldCountryCode = sharedPreference.getString(UserConstant.countryCode, "")
        val oldPhoneNumber = sharedPreference.getString(UserConstant.phoneNumber, "")
//        if (countryCode == oldCountryCode && phoneNumber == oldPhoneNumber) {
//            Utils.showNoticeDialog(this, msg = "Phone number must be different")
//            return
//        }

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putLong("otpTimeStamp", currentTime)
                .apply()

        requestHttp(UrlConstant.SEND_OTP, hashMapOf(
                "country_code" to countryCode,
                "phone_number" to phoneNumber,
                "via" to "sms"
        )) {
            goNext()
        }
    }

    /**
     *  Events
     */
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