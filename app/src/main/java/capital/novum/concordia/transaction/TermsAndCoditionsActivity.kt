package capital.novum.concordia.transaction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.transaction_terms_conditions_activity.*

class TermsAndCoditionsActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_terms_conditions_activity
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { goNext() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    fun goNext() {
        val intent = Intent(this, InputWalletActivity::class.java)
        startActivity(intent)
    }
}