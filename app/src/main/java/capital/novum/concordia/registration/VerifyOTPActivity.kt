package capital.novum.concordia.registration

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceManager
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.verify_otp_activity.*

class VerifyOTPActivity : BaseActivity(){
    var countryCode: String = ""
    var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryCode = intent.getStringExtra("countryCode")
        phoneNumber = intent.getStringExtra("phoneNumber")
    }
    /*
        Custom Views
     */

    override fun getLayoutId(): Int {
        return R.layout.verify_otp_activity
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { verifyOTP() }
        btnResend.setOnClickListener { reSendOTP() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "Verification OTP"
    }

    /**
     *  Call API
     */
    private fun verifyOTP() {
        val params = hashMapOf(
                "country_code" to countryCode,
                "phone_number" to phoneNumber,
                "otp_code" to otpCodeEdt.text.toString()
        )
        requestHttp(UrlConstant.VERIFY_OTP, params) {
            goBack()
        }
    }

    private fun reSendOTP() {
        val currentTime = System.currentTimeMillis()
        val otpTimeStamp = PreferenceManager.getDefaultSharedPreferences(this).getLong("otpTimeStamp", 0)
        if (currentTime - otpTimeStamp < 60000L) {
            Utils.showNoticeDialog(this, msg = "You can resend OTP after 60 seconds")
            return
        }

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putLong("otpTimeStamp", currentTime)
                .apply()

        val params = hashMapOf(
                "country_code" to countryCode,
                "phone_number" to phoneNumber,
                "via" to "sms"
        )
        requestHttp(UrlConstant.SEND_OTP, params) {
            Utils.showNoticeDialog(this, msg = "New OTP was sent")
        }
    }
    /**
     *  Navigation
     */
    fun goBack() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(UserConstant.countryCode, countryCode)
                .putString(UserConstant.phoneNumber, phoneNumber)
                .putLong("otpTimeStamp", 0)
                .apply()
        setResult(Activity.RESULT_OK)
        finish()
    }
}