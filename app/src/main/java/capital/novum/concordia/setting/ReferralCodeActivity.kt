package capital.novum.concordia.setting

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.setting.adapter.CvenBonusAdapter
import kotlinx.android.synthetic.main.setting_referral_code_activity.*

class ReferralCodeActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_referral_code_activity
    }

    override fun customViews() {
        super.customViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CvenBonusAdapter(arrayOf("1"))
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "REFERRAL PROGRAMME"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}