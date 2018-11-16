package capital.novum.concordia.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.main.ProjectListActivity

class RegistrationSuccessActivity: BaseActivity() {
    /*
    *   Custom Views
    */

    override fun getLayoutId(): Int {
        return R.layout.registration_success_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "NEW USER REGISTRATION"
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}