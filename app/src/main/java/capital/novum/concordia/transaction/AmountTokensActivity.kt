package capital.novum.concordia.transaction

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.service.CoinMarketService
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.transaction_amount_token_activity.*

class AmountTokensActivity : BaseActivity(), TextWatcher {
    var projectId: Int = 0
    var paymentMethod = "ETH"
    var paymentMethodId = 2
    var projectLink = ""
    var projectName = ""
    var walletAddress = ""
    var tokenPrice = ""
    var discount = ""
    var ethPrice: Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        projectId = intent.getIntExtra("projectId", 5)
        projectLink = intent.getStringExtra("projectLink")
        projectName = intent.getStringExtra("projectName")
        paymentMethod = intent.getStringExtra("paymentMethod")
        paymentMethodId = intent.getIntExtra("paymentMethodId", 2)
        walletAddress = intent.getStringExtra("walletAddress")
        tokenPrice = intent.getStringExtra("tokenPrice")
        discount = intent.getStringExtra("discount")
        super.onCreate(savedInstanceState)
    }

    /*
        Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.transaction_amount_token_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(3)
        header.setProjectTitle(projectName)
        header.setProjectIcon(projectLink)
        tokenPriceTxt.text = "1 TOKEN = $tokenPrice $paymentMethod"
        amountTitle.text = "Total $paymentMethod amount:"
        updatePaymentAmount()
        btnNext.setOnClickListener { participate() }
        tokenAmountEdt.addTextChangedListener(this)
        if (paymentMethod !in arrayOf("ETH", "CVEN"))
            getETHRate()
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */
    override fun afterTextChanged(s: Editable?) {
        updatePaymentAmount()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    /**
     *  Call API
     */
    private fun getETHRate() {
        showProgressSpinner()
        val observer = CoinMarketService.create().getCryptoCurrencyQuotes(convert = paymentMethod)
        disposable = observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.status.errorCode != 0) {
                        Utils.showNoticeDialog(this, msg = result.status.errorMessage)
                    } else {
                        val quote = result.data.ETH.quote
                        ethPrice = when (paymentMethod) {
                            "BTC" -> quote.BTC.price
                            "XLM" -> quote.XLM.price
                            "USD" -> quote.USD.price
                            else -> 1.0
                        }
                    }
                }
    }

    private fun participate() {
        showProgressSpinner()
        val token = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.token, "")
        val paymentAmountETH = paymentAmountTxt.text.toString().toDouble() / ethPrice
        Log.e("Amount Token", paymentAmountETH.toString())
        val observer = concordiaService.participate(token, projectId, paymentMethod,
                tokenAmountEdt.text.toString(), paymentMethodId, paymentAmountTxt.text.toString(),
                walletAddress, discount, referralCodeEdt.text.toString(), "$paymentAmountETH")
        disposable = observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(this, msg = result.message)
                    } else {
                        if (paymentMethod == "USD") {
                            goToUsdDetail()
                        } else {
                            goToEthDetail()
                        }
                    }
                }
    }

    /**
     *  Navigation
     */
    fun goToUsdDetail() {
        val intent = Intent(this, USDDetailActivity::class.java)
        intent.putExtra("projectId", projectId)
        intent.putExtra("paymentAmount", paymentAmountTxt.text.toString())
        intent.putExtra("paymentMethod", paymentMethod)
        startActivity(intent)
    }

    fun goToEthDetail() {
        val intent = Intent(this, ETHDetailActivity::class.java)
        intent.putExtra("projectId", projectId)
        intent.putExtra("paymentAmount", paymentAmountTxt.text.toString())
        intent.putExtra("paymentMethod", paymentMethod)
        startActivity(intent)
    }

    /**
     *  Logic
     */
    private fun updatePaymentAmount() {
        val tokenAmount = tokenAmountEdt.text.toString()
        val amount: Double = if (tokenAmount.isEmpty()) 0.0 else tokenPrice.toDouble() * tokenAmount.toDouble()
        paymentAmountTxt.text = "$amount"
    }
}