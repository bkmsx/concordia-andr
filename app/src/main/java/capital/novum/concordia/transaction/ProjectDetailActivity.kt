package capital.novum.concordia.transaction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.share.ShareInformationActivity
import kotlinx.android.synthetic.main.project_detail_activity.*

class ProjectDetailActivity : BaseActivity() {
    /*
        Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.project_detail_activity
    }

    override fun customViews() {
        super.customViews()
        for (i in 1..3) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_discount_tier, null)
            contentView.addView(view)
        }

        btnParticipate.setOnClickListener { goToTermsAndCoditions() }
        btnInviteFriend.setOnClickListener { goToShareInformation() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "W GREEN PAY"
    }

    /*
        Events
     */

    private fun goToTermsAndCoditions() {
        val intent = Intent(this, TermsAndCoditionsActivity::class.java)
        startActivity(intent)
    }

    private fun goToShareInformation() {
        val intent = Intent(this, ShareInformationActivity::class.java)
        startActivity(intent)
    }
}