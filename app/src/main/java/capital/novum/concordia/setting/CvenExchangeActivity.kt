package capital.novum.concordia.setting

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.setting.adapter.CvenBonusAdapter
import capital.novum.concordia.setting.adapter.CvenFragmentAdapter
import kotlinx.android.synthetic.main.setting_cven_exchange_activity.*
import kotlinx.android.synthetic.main.setting_cven_exchange_activity.view.*
import kotlinx.android.synthetic.main.setting_referral_code_activity.*

class CvenExchangeActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_cven_exchange_activity
    }

    override fun customViews() {
        super.customViews()
        viewpager.adapter = CvenFragmentAdapter(this, supportFragmentManager)
        tablayout.setupWithViewPager(viewpager)
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "CVEN EXCHANGE"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}