package capital.novum.concordia.setting

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class ReferralCodeActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_referral_code_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "REFERRAL PROGRAMME"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}