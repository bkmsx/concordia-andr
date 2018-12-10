package capital.novum.concordia.transaction

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.LoginResult
import capital.novum.concordia.model.ProjectDetail
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.util.Constants
import capital.novum.concordia.util.FingerprintUtil
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.transaction_terms_conditions_activity.*

class TermsAndConditionsActivity : BaseActivity() {
    var projectId = 0
    var projectUrl = ""
    val fingerprintUtil by lazy { FingerprintUtil(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectId = intent.getIntExtra("projectId", 0)
        getProjectDetail()
    }

    /*
        Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.transaction_terms_conditions_activity
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { checkTermsAgreement() }
        btnDialog.setOnClickListener {
            Utils.blinkView(this, it)
            Utils.showTermConditions(this, projectUrl)
        }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */
    private fun checkTermsAgreement() {
        if (checkbox1.isChecked && checkbox2.isChecked) {
            authenticate()
        } else {
            Utils.showNoticeDialog(this, msg = "Please check the boxes if you accept the Terms and Conditions for this project. " +
                    "Otherwise, please do not participate.")
        }
    }

    private fun authenticate() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val securityEnable = sharedPreferences.getString(UserConstant.deviceSecurityEnable, "false")
        val email = sharedPreferences.getString(UserConstant.email, "")
        if (securityEnable != "false" && fingerprintUtil.canUseFingerprint()) {
            fingerprintUtil.startAuth(title = "Authenticate Your Fingerprint", msg = ""){
                goNext()
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

    /**
     *  Call API
     */
    private fun getProjectDetail() {
        requestHttp(UrlConstant.PROJECT_DETAIL, hashMapOf("project_id" to projectId.toString())) {
            val result = it as ProjectDetail
            val project = result.project!!
            projectUrl = project.termsUrl
            header.setProjectIcon(project.logo)
            header.setProjectTitle(project.title)
        }
    }

    private fun loginAccount(params: HashMap<String, String>) {
        requestHttp(UrlConstant.LOGIN_ACCOUNT, params) {
            val result = it as LoginResult
            LocalData.saveUserDetail(this, result.user!!)
            Log.e("Login", result.user!!.token)
            goNext()
        }
    }

    /**
     *  Navigations
     */
    private fun goNext() {
        val intent = Intent(this, InputWalletActivity::class.java)
        intent.putExtra("projectId", projectId)
        startActivity(intent)
    }
}