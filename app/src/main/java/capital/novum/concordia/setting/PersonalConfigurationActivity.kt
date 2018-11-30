package capital.novum.concordia.setting

import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.Result
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.setting_personal_configuration_activity.*

class PersonalConfigurationActivity : BaseActivity() {

    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_personal_configuration_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "SETTINGS"
    }

    override fun customViews() {
        super.customViews()
        val touchEnabled = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.deviceSecurityEnable, "false")
        if (touchEnabled == "true") {
            btnYes.isChecked = true
        } else {
            btnNo.isChecked = true
        }
        btnNext.setOnClickListener { updateConfiguration() }
    }

    /*
        Events
     */

    private fun updateConfiguration() {
        val params = hashMapOf(
                "device_security_enable" to "${btnYes.isChecked}"
        )
        requestHttp(UrlConstant.CHANGE_CONFIGURATION, params) {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString(UserConstant.deviceSecurityEnable, "${btnYes.isChecked}").apply()
            Utils.showNoticeDialog(this, msg = "Configuration was updated") {
                finish()
            }
        }
    }
}