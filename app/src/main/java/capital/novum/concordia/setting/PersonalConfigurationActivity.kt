package capital.novum.concordia.setting

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

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

    /*
        Events
     */

    fun goNext(view : View) {

    }
}