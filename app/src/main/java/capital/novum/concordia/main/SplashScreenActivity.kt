package capital.novum.concordia.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import capital.novum.concordia.R
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.registration.RegistrationActivity
import capital.novum.concordia.registration.RegistrationSuccessActivity
import capital.novum.concordia.transaction.TestActivity
import capital.novum.concordia.util.Constants

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sharedPreferences.getString(UserConstant.token, "")
        Log.e("Splash", token)
        val registered = sharedPreferences.getBoolean(Constants.REGISTED, false)
        val intent = Intent(applicationContext, if (registered) LoginActivity::class.java else SlideShowActivity::class.java)
        val slide = Slide()
        slide.setDuration(500)
        slide.slideEdge = Gravity.END
        window.exitTransition = slide
        val handler = Handler()
        handler.postDelayed(Runnable {
            kotlin.run {
                startActivity(intent)
                finish()
            }
        }, 700)
    }
}
