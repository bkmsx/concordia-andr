package capital.novum.concordia.setting

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class EditWalletActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_edit_wallet_activity
    }

    override fun customViews() {
        super.customViews()
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE WALLET"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}