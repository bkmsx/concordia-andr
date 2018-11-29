package capital.novum.concordia.setting

import android.os.Bundle
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.ParticipateDetailResult
import capital.novum.concordia.model.ParticipateHistory
import capital.novum.concordia.util.UrlConstant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.usd_participate_detail.*

class UsdParticipateDetail : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getParticipateDetail()
    }
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

    private fun setupLayout(history: ParticipateHistory?) {
        Picasso.get().load(history?.logo).into(logoImg)
        titleTxt.text = history?.title?.toUpperCase()
        priceTxt.text = "1 TOKEN = ${history?.pricePerToken} ${history?.paymentMode}"
        tokenAmountTxt.text = "Tokens purchased: ${history?.tokensPurchased}"
        paymentMethodTxt.text = "Payment method: ${history?.paymentMode}"
        paymentAmountTxt.text = "Total amount: ${history?.amount}"
        val paymentDestination = history?.paymentDestination
        bankNameTxt.text = paymentDestination?.bankName
        swiftCodeTxt.text = paymentDestination?.swiftCode
        bankAddressTxt.text = paymentDestination?.bankAddress
        accountNameTxt.text = paymentDestination?.accountName
        accountNumberTxt.text = paymentDestination?.accountNumber
        businessAddressTxt.text = paymentDestination?.holderAddress
    }

    /**
     *  Call API
     */
    private fun getParticipateDetail(){
        val historyId = intent.getIntExtra("historyId", 0)
        requestHttp(UrlConstant.PARTICIPATE_DETAIL, hashMapOf("history_id" to "$historyId")){
            val result = it as ParticipateDetailResult
            setupLayout(result.historyDetail)
        }
    }
}