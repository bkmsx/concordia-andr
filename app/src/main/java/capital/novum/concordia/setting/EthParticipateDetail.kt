package capital.novum.concordia.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.ParticipateDetailResult
import capital.novum.concordia.model.ParticipateHistory
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.share.ShareMethodsActivity
import capital.novum.concordia.util.LoadQrCodeAsync
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.eth_participate_detail.*

class EthParticipateDetail : BaseActivity() {
    lateinit var history: ParticipateHistory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getParticipateDetail()
    }
    /**
     *  Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.eth_participate_detail
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "HISTORY DETAIL"
    }

    private fun setupLayout(history: ParticipateHistory?) {
        Picasso.get().load(history?.logo).into(logoImg)
        titleTxt.text = history?.title
        priceTxt.text = "1 TOKEN = ${history?.pricePerToken} ${history?.paymentMode}"
        tokenAmountTxt.text = "Token purchased: ${history?.tokensPurchased}"
        paymentMethodTxt.text = "Payment method: ${history?.paymentMode}"
        paymentAmountTxt.text = "Total amount: ${history?.amount}"
        val address = history?.paymentDestination?.walletAddress!!
        walletAddress.setText(address)
        LoadQrCodeAsync{
            qrAddress.setImageBitmap(it)
            progressBar.visibility = View.GONE
        }.execute(address)
        btnNext.setOnClickListener { gotoShareInformation() }
    }

    /**
     *  Call API
     */
    private fun getParticipateDetail() {
        val historyId = intent.getIntExtra("historyId", 0)
        requestHttp(UrlConstant.PARTICIPATE_DETAIL, hashMapOf("history_id" to historyId.toString())) {
            val result = it as ParticipateDetailResult
            history = result.historyDetail!!
            setupLayout(history)
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