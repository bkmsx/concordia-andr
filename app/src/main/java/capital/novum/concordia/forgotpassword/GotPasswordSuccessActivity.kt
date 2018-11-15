package capital.novum.concordia.forgotpassword

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity

class GotPasswordSuccessActivity : BaseActivity() {
    /*
        Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.got_password_success_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "FORGOT PASSWORD"
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}