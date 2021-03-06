package capital.novum.concordia.setting

import android.preference.PreferenceManager
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.setting_change_password_activity.*

class ChangePasswordActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_change_password_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE PASSWORD"
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { updatePassword() }
    }

    /**
     *  Call API
     */

    fun updatePassword() {
        val oldPass = oldPasswordEdt.text.toString()
        val newPass = newPasswordEdt.text.toString()
        val confirmPass = confirmPassEdt.text.toString()

        if (oldPass == "") {
            Utils.showNoticeDialog(this, msg = "Please input current password")
            return
        }

        if (oldPass == newPass) {
            Utils.showNoticeDialog(this, msg = "Please use different password")
            return
        }

        if (newPass != confirmPass) {
            Utils.showNoticeDialog(this, msg = "Password don\'t match")
            return
        }

        val params = hashMapOf(
                "password" to newPass,
                "old_password" to oldPass
        )
        requestHttp(UrlConstant.CHANGE_PASSWORD, params) {
            Utils.showNoticeDialog(this, msg = "Password was updated") {
                finish()
            }
        }
    }
}