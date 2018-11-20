package capital.novum.concordia.setting

import android.content.Intent
import android.graphics.Color
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.setting_update_passport_activity.*

class UpdatePassportActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_update_passport_activity
    }

    override fun customViews() {
        super.customViews()
        spinnerCitizenship.changeBackground(R.drawable.gray_bottom_line_bg)
        spinnerCitizenship.setData(arrayOf("VietNamese", "Singaporean"))
        spinnerCitizenship.changeTextColor(Color.WHITE)
        spinnerCountry.changeBackground(R.drawable.gray_bottom_line_bg)
        spinnerCountry.setData(arrayOf("VietNam", "Singapore"))
        spinnerCountry.changeTextColor(Color.WHITE)
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE PASSPORT DETAILS"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}