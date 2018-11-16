package capital.novum.concordia.registration

import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import fr.ganfra.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.register_information_activity.*

class RegisterInformationActivity : BaseActivity(){
    /*
        Custom Views
     */

    override fun getLayoutId(): Int {
        return R.layout.register_information_activity
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
        val intent = Intent(this, RegistrationSuccessActivity::class.java)
        startActivity(intent)
    }
}