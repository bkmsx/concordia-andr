package capital.novum.concordia.main

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.EditText
import capital.novum.concordia.R
import capital.novum.concordia.forgotpassword.ForgotPasswordActivity
import capital.novum.concordia.model.Citizenship
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.Nationality
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.registration.RegistrationActivity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    /**
     *      Events
     */

    fun login(view : View) {
        val email = findViewById<EditText>(R.id.edt_email).text.toString()
        val password = findViewById<EditText>(R.id.edt_password).text.toString()
        disposable = concordiaService.loginAccount(email, password, "123", "Android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                   result -> Log.e("Login", result.toString())
                    LocalData.saveUserDetail(this, result.user)
                    gotoProjectList()
                }

    }

    /**
     *      Navigation
     */

    fun gotoProjectList() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun gotoRegister(view : View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    fun gotoForgotPassword(view : View) {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

}