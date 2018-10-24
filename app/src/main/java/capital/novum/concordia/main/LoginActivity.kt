package capital.novum.concordia.main

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun login(view : View) {
        disposable = concordiaService.loginAccount("tien@novum.capital", "123456", "123", "Android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                   result -> Log.e("Login", result.toString())
                    LocalData.saveUserDetail(this, result.user)
                }

    }
    fun register(view : View) {
        disposable = concordiaService.getNationalities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    result -> Log.e("Login", result.toString())
                }
    }

    fun forgotPassword(view : View) {
        Log.e("Login", PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.firstName, "No name"))
        Log.e("Login", PreferenceManager.getDefaultSharedPreferences(this).getInt(UserConstant.id, 1).toString())
    }

}