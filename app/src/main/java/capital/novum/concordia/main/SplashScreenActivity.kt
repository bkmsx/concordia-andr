package capital.novum.concordia.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.view.Gravity
import capital.novum.concordia.R
import capital.novum.concordia.registration.RegistrationActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)
        val intent = Intent(applicationContext, LoginActivity::class.java)
        var slide = Slide()
        slide.setDuration(500)
        slide.slideEdge = Gravity.RIGHT
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
