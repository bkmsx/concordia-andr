package capital.novum.concordia.forgotpassword

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity
import kotlinx.android.synthetic.main.forgot_password_activity.*

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

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { goNext() }
    }

    /*
        Events
     */

    fun goNext() {
        val intent = Intent(this, GotPasswordSuccessActivity::class.java)
        startActivity(intent)
    }

}