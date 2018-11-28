package capital.novum.concordia.setting

import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.UserConstant
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
        val token = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.token, "")
        val params = hashMapOf(
                "device_security_enable" to "${btnYes.isChecked}"
        )
        showProgressSpinner()
        val observer = concordiaService.changePersonalCofiguration(token, params)
        disposable = observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(this, msg = result.message)
                    } else {
                        PreferenceManager.getDefaultSharedPreferences(this).edit()
                                .putString(UserConstant.deviceSecurityEnable, "${btnYes.isChecked}").apply()
                        Utils.showNoticeDialog(this, msg = "Configuration was updated") {
                            finish()
                        }
                    }
                }
    }
}