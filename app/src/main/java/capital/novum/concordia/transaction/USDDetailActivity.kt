package capital.novum.concordia.transaction

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.transaction_usd_detail_activity.*

class USDDetailActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_usd_detail_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(4)
    }

    override fun setupToolBar() {
        super.setupToolBar()
        leftToolbarButton.setImageResource(R.mipmap.back_blue)
        leftToolbarButton.visibility = View.VISIBLE
        toolbarTitle.text = "PARTICIPATE"
        rightToolbarButton.visibility = View.INVISIBLE
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, InputWalletActivity::class.java)
        startActivity(intent)
    }
}