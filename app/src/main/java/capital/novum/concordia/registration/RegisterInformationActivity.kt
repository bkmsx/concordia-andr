package capital.novum.concordia.registration

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class RegisterInformationActivity : BaseActivity(){
    /*
        Custom Views
     */

    override fun getLayoutId(): Int {
        return R.layout.register_information_activity
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
        val intent = Intent(this, RegistrationSuccessActivity::class.java)
        startActivity(intent)
    }
}