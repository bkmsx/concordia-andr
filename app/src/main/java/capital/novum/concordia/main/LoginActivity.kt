package capital.novum.concordia.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.forgotpassword.ForgotPasswordActivity
import capital.novum.concordia.registration.RegistrationActivity

class LoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun login(view : View) {
        var intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
    }
    fun register(view : View) {
        var intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
    fun forgotPassword(view : View) {
        var intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
}