package capital.novum.concordia.setting

import android.content.Intent
import android.os.Bundle
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.ParticipateDetailResult
import capital.novum.concordia.model.ParticipateHistory
import capital.novum.concordia.model.Project
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.share.ShareMethodsActivity
import capital.novum.concordia.util.UrlConstant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.usd_participate_detail.*

class UsdParticipateDetail : BaseActivity() {
    lateinit var history: ParticipateHistory
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
        btnNext.setOnClickListener { gotoShareInformation() }
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
        requestHttp(UrlConstant.PARTICIPATE_DETAIL, hashMapOf("history_id" to historyId.toString())){
            val result = it as ParticipateDetailResult
            history = result.historyDetail!!
            setupLayout(result.historyDetail)
        }
    }

    /**
     *  Navigations
     */
    private fun gotoShareInformation() {
        val intent = Intent(this, if (history.promotion == 0)
            ShareMethodsActivity::class.java else ShareInformationActivity::class.java).apply {
            putExtra("projectId", history.projectId)
        }
        startActivity(intent)
    }
}