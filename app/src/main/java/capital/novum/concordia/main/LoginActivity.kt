package capital.novum.concordia.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import capital.novum.concordia.R
import capital.novum.concordia.forgotpassword.ForgotPasswordActivity
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.LoginResult
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.registration.RegistrationActivity
import capital.novum.concordia.util.Constants
import capital.novum.concordia.util.FingerprintUtil
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : BaseActivity() {
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val email = sharedPreferences.getString(UserConstant.email, null) ?: return
        edtEmail.setText(email, TextView.BufferType.EDITABLE)
        val securityEnable = sharedPreferences.getString(UserConstant.deviceSecurityEnable, "false")
        if (securityEnable == "false") return
        Handler().postDelayed({
            askForFingerprint(email)
        },500)
    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    override fun customViews() {
        super.customViews()
        loginBtn.setOnClickListener { login() }
        forgotPasswordBtn.setOnClickListener {
            Utils.blinkView(this, forgotPasswordBtn)
            gotoForgotPassword()
        }

        registerBtn.setOnClickListener {
            Utils.blinkView(this, registerBtn)
            gotoRegister()
        }
    }

    /**
     *      Events
     */
    private fun askForFingerprint(email: String) {
        val fingerprintUtil = FingerprintUtil(this)
        if (fingerprintUtil.canUseFingerprint()){
            fingerprintUtil.startAuth("Login With Fingerprint", email) {
                loginWithSecurityToken()
            }
        }
    }

    fun login() {
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        if (email.isEmpty()) {
            Utils.showNoticeDialog(this, msg = "Email is empty")
            return
        }
        if (password.isEmpty()) {
            Utils.showNoticeDialog(this, msg = "Password is empty")
            return
        }
        val deviceId = sharedPreferences.getString(Constants.DEVICE_ID, "")
        val params = hashMapOf(
                "email" to email,
                "password" to password,
                "device_id" to deviceId,
                "platform" to "Android"
        )
        loginAccount(params)
    }

    private fun loginWithSecurityToken() {
        val deviceId = sharedPreferences.getString(Constants.DEVICE_ID, "")
        val params = hashMapOf(
                "email" to edtEmail.text.toString(),
                "security_token" to sharedPreferences.getString(UserConstant.securityToken, ""),
                "device_id" to deviceId,
                "platform" to "Android"
        )
        loginAccount(params)
    }

    /**
     *  Call API
     */
    private fun loginAccount(params: HashMap<String, String>) {
        requestHttp(UrlConstant.LOGIN_ACCOUNT, params) {
            val result = it as LoginResult
            LocalData.saveUserDetail(this, result.user!!)
            Log.e("Login", result.user!!.deviceId)
            sharedPreferences.edit().putBoolean(Constants.REGISTED, true).apply()
            gotoProjectList()
        }
    }

    /**
     *  Navigation
     */

    private fun gotoProjectList() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun gotoRegister() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    fun gotoForgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
}