package capital.novum.concordia.share

import android.content.Intent
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.transaction_usd_detail_activity.*

class ShareInformationActivity : BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.share_information_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "SHARE WITH FRIENDS"
        btnNext.setOnClickListener { goToShareMethods() }
    }

    /**
     *  Events
     */


    /**
     *  Navigations
     */
    private fun goToShareMethods() {
        val intent = Intent(this, ShareMethodsActivity::class.java)
        startActivity(intent)
    }
}