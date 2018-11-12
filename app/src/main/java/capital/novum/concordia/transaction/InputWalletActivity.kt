package capital.novum.concordia.transaction

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.transaction_input_wallet_activity.*

class InputWalletActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_input_wallet_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(2)
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
        val intent = Intent(this, AmountTokensActivity::class.java)
        startActivity(intent)
    }
}