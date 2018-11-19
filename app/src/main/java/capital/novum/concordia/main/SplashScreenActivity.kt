package capital.novum.concordia.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.view.Gravity
import capital.novum.concordia.R
import capital.novum.concordia.forgotpassword.ForgotPasswordActivity
import capital.novum.concordia.forgotpassword.GotPasswordSuccessActivity
import capital.novum.concordia.registration.RegisterInformationActivity
import capital.novum.concordia.registration.RegistrationActivity
import capital.novum.concordia.registration.RegistrationSuccessActivity
import capital.novum.concordia.registration.VerifyOTPActivity
import capital.novum.concordia.setting.EditWalletActivity
import capital.novum.concordia.setting.PersonalConfigurationActivity
import capital.novum.concordia.setting.WalletListActivity
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.share.ShareMethodsActivity
import capital.novum.concordia.transaction.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)
        val intent = Intent(applicationContext, EditWalletActivity::class.java)
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
