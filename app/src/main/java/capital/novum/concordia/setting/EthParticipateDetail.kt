package capital.novum.concordia.setting

import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.eth_participate_detail.*

class EthParticipateDetail : BaseActivity() {
    val WALLET_ADDRESS = "0xa26Bc0Cd31D9debc987890112C4363336a19a5F9"
    /**
     *  Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.eth_participate_detail
    }

    override fun customViews() {
        super.customViews()
        walletAddress.setText(WALLET_ADDRESS)
        qrAddress.setImageBitmap(Utils.getQrCode(WALLET_ADDRESS))
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "HISTORY DETAIL"
    }

}