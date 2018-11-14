package capital.novum.concordia.transaction

import android.content.Intent
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.test_activity.*
import kotlinx.android.synthetic.main.transaction_input_wallet_activity.*

class TestActivity : BaseActivity() {
    private val tag = javaClass.toString()
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.test_activity
    }

    override fun customViews() {
        super.customViews()
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

    fun openSpinner(view: View) {
    }
}