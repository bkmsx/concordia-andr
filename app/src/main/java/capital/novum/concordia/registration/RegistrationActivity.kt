package capital.novum.concordia.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.ProjectListActivity
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.registration_activity.*

class RegistrationActivity: BaseActivity() {
    /*
    *   Custom Views
    */

    override fun getLayoutId(): Int {
        return R.layout.registration_activity
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
        val intent = Intent(this, VerifyOTPActivity::class.java)
        startActivity(intent)
    }
}