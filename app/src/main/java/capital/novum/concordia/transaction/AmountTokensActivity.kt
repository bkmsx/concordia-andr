package capital.novum.concordia.transaction

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.transaction_input_wallet_activity.*

class AmountTokensActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_amount_token_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(3)
        btnNext.setOnClickListener { goToUsdDetail() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    fun goToUsdDetail() {
        val intent = Intent(this, USDDetailActivity::class.java)
        startActivity(intent)
    }

    fun goToEthDetail() {
        val intent = Intent(this, ETHDetailActivity::class.java)
        startActivity(intent)
    }
}