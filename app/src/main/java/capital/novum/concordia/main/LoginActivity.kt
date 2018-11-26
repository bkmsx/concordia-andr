package capital.novum.concordia.main

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.forgotpassword.ForgotPasswordActivity
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.LoginResult
import capital.novum.concordia.registration.RegistrationActivity
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import java.util.HashMap

class LoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    /**
     *      Events
     */

    fun login(view: View) {

        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        if (email.isEmpty()) {
            Utils.showNoticeDialog(this, msg = "Email is empty")
            return
        }
        if (password.isEmpty()) {
            Utils.showNoticeDialog(this, msg = "Password is empty")
            return
        }

        loginAccount(email, password)
    }

    /**
     *  Call API
     */
    private fun loginAccount(email: String, password: String) {
        showProgressSpinner()
        val observer = concordiaService.loginAccount(email, password, "123", "Android")
        disposable = observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(this, msg = result.message)
                    } else {
                        LocalData.saveUserDetail(this, result.user)
                        gotoProjectList()
                    }
                }
    }

    /**
     *      Navigation
     */

    private fun gotoProjectList() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun gotoRegister(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    fun gotoForgotPassword(view: View) {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

}