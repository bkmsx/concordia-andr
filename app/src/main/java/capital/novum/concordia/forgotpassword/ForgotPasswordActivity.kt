package capital.novum.concordia.forgotpassword

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        btnNext.setOnClickListener { retrievePassword() }
    }

    /*
        Events
     */

    /**
     *  Call API
     */
    private fun retrievePassword() {
        val email = emailEdt.text.toString()
        if (email == "") {
            Utils.showNoticeDialog(this, msg = "Please input your email")
            return
        }
        requestHttp(UrlConstant.FORGOT_PASSWORD, hashMapOf("email" to email)){
            goNext()
        }
    }

    /**
     *  Navigations
     */
    private fun goNext() {
        val intent = Intent(this, GotPasswordSuccessActivity::class.java)
        startActivity(intent)
    }

}