package capital.novum.concordia.transaction

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.main.ProjectListActivity
import capital.novum.concordia.share.ShareInformationActivity
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
        toolbarTitle.text = "PARTICIPATE"
        rightToolbarButton.visibility = View.VISIBLE
        rightToolbarButton.setImageResource(R.mipmap.done_blue)
        btnNext.setOnClickListener { goToShareInformation() }
    }

    override fun rightToolbarClick() {
        super.rightToolbarClick()
        goToProjectList()
    }

    /**
     *  Navigations
     */
    fun goToShareInformation() {
        val intent = Intent(this, ShareInformationActivity::class.java)
        startActivity(intent)
    }

    fun goToProjectList() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }
}