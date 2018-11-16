package capital.novum.concordia.setting

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

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

    /*
        Events
     */

    fun goNext(view : View) {

    }
}