package capital.novum.concordia.registration

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.verify_otp_activity.*

class VerifyOTPActivity : BaseActivity(){
    /*
        Custom Views
     */

    override fun getLayoutId(): Int {
        return R.layout.verify_otp_activity
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { goNext() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "NEW USER REGISTRATION"
    }

    /*
       Events
    */

    fun goNext() {
        val intent = Intent(this, RegisterInformationActivity::class.java)
        startActivity(intent)
    }
}