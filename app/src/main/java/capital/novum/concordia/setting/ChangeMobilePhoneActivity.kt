package capital.novum.concordia.setting

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class ChangeMobilePhoneActivity : BaseActivity() {
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

    /*
        Events
     */

    fun goNext(view : View) {

    }
}