package capital.novum.concordia.transaction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
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
    }

    override fun setupToolBar() {
        super.setupToolBar()
        leftToolbarButton.setImageResource(R.mipmap.back_blue)
        leftToolbarButton.visibility = View.VISIBLE
        toolbarTitle.text = "W GREEN PAY"
        rightToolbarButton.visibility = View.INVISIBLE
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, TermsAndCoditionsActivity::class.java)
        startActivity(intent)
    }

}