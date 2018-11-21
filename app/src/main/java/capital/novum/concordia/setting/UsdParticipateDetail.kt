package capital.novum.concordia.setting

import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class UsdParticipateDetail : BaseActivity() {
    val WALLET_ADDRESS = "0xa26Bc0Cd31D9debc987890112C4363336a19a5F9"
    /**
     *  Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.usd_participate_detail
    }

    override fun customViews() {
        super.customViews()
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "HISTORY DETAIL"
    }

}