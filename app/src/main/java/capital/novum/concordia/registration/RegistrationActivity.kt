package capital.novum.concordia.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.ProjectListActivity
import com.hbb20.CountryCodePicker

class RegistrationActivity: BaseActivity() {
    /*
    *   Custom Views
    */

    override fun getLayoutId(): Int {
        return R.layout.registration_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        leftToolbarButton.setImageResource(R.mipmap.back_blue)
        leftToolbarButton.visibility = View.VISIBLE
        toolbarTitle.text = "NEW USER REGISTRATION"
        rightToolbarButton.visibility = View.INVISIBLE
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, VerifyOTPActivity::class.java)
        startActivity(intent)
    }
}