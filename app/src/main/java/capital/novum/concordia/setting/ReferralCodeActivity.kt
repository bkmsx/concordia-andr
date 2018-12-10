package capital.novum.concordia.setting

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.ReferralBonus
import capital.novum.concordia.model.ReferralListResult
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.setting.adapter.CvenBonusAdapter
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.setting_referral_code_activity.*

class ReferralCodeActivity : BaseActivity() {
    lateinit var adapter: CvenBonusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getReferralBonusList()
    }
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_referral_code_activity
    }

    override fun customViews() {
        super.customViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CvenBonusAdapter()
        recyclerView.adapter = adapter
        val referralBy = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.referredBy, null)
        if (referralBy != null) referralCodeLayout.visibility = View.GONE
        btnUpdate.setOnClickListener { updateReferralCode() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "REFERRAL PROGRAMME"
    }

    /**
     *  Events
     */

    /**
     *  Call API
     */
    private fun updateReferralCode() {
        val referralCode = referralCodeEdt.text.toString()
        if (referralCode == "") {
            Utils.showNoticeDialog(this, msg = "Please input referral code")
            return
        }
        requestHttp(UrlConstant.UPDATE_REFERRAL_CODE, hashMapOf("referral_code" to referralCode)) {
            referralCodeLayout.visibility = View.GONE
        }
    }

    private fun getReferralBonusList() {
        requestHttp(UrlConstant.LIST_REFERRAL_BONUS){
            val result = it as ReferralListResult
            adapter.data = result.referralList
            var totalPoint: Double = 0.0
            result.referralList.forEach { totalPoint += it.amount.toDouble() }
            totalPointTxt.text = "$totalPoint"
        }
    }
}