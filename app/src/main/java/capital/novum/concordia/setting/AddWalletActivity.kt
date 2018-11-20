package capital.novum.concordia.setting

import android.graphics.Color
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.setting_add_wallet_activity.*

class AddWalletActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_add_wallet_activity
    }

    override fun customViews() {
        super.customViews()
        spinnerWalletType.setData(arrayOf("ETH", "USD"))
        spinnerWalletType.setText("ETH")
        spinnerWalletType.changeTextColor(Color.WHITE)
        spinnerWalletType.changeBackground(R.drawable.blue_bottom_line_bg)
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "ADD NEW WALLET"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}