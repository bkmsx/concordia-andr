package capital.novum.concordia.setting

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.setting.adapter.WalletAdapter
import kotlinx.android.synthetic.main.setting_wallet_list_activity.*

class WalletListActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_wallet_list_activity
    }

    override fun customViews() {
        super.customViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WalletAdapter(arrayOf("12"))
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE WALLETS"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}