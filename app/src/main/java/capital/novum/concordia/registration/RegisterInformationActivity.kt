package capital.novum.concordia.registration

import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import fr.ganfra.materialspinner.MaterialSpinner

class RegisterInformationActivity : BaseActivity(){
    /*
        Custom Views
     */

    override fun getLayoutId(): Int {
        return R.layout.register_information_activity
    }

    override fun customViews() {
        super.customViews()
        val spinnerCitizenship = findViewById<MaterialSpinner>(R.id.spinner_citizenship)
        val items = arrayOf("Vietnam", "Singapore")
        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCitizenship.adapter = adapter
        val spinnerCountry = findViewById<MaterialSpinner>(R.id.spinner_country)
        spinnerCountry.adapter = adapter
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