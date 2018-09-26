package capital.novum.concordia

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val intent = Intent(applicationContext, MainActivity::class.java)
        val handler = Handler()
        handler.postDelayed(Runnable {
            kotlin.run {
                startActivity(intent)
                finish()
            }
        }, 700)

    }
}
