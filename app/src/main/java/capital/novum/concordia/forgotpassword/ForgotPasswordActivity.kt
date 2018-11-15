package capital.novum.concordia.forgotpassword

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity

class ForgotPasswordActivity : BaseActivity() {
    /*
        Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.forgot_password_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "FORGOT PASSWORD"
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, GotPasswordSuccessActivity::class.java)
        startActivity(intent)
    }

}