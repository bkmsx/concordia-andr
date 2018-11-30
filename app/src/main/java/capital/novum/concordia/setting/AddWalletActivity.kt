package capital.novum.concordia.setting

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.AppPaymentMethod
import capital.novum.concordia.model.PaymentMethodListResult
import capital.novum.concordia.service.ConcordiaService
import capital.novum.concordia.transaction.QrScannerActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import com.google.gson.FieldNamingPolicy
import kotlinx.android.synthetic.main.setting_add_wallet_activity.*

class AddWalletActivity : BaseActivity() {
    lateinit var paymentMethods : List<AppPaymentMethod>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        concordiaService = ConcordiaService.create(FieldNamingPolicy.IDENTITY)
        getPaymentMethod()
    }
    /**
     *  Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_add_wallet_activity
    }

    override fun customViews() {
        super.customViews()
        spinnerWalletType.setText("ETH")
        spinnerWalletType.changeTextColor(Color.WHITE)
        spinnerWalletType.changeBackground(R.drawable.blue_bottom_line_bg)

        btnScan.setTitle("Scan QR")
        btnScan.setIcon(R.mipmap.blue_scan)
        btnScan.setOnClickListener { gotoScanner() }

        btnNext.setOnClickListener { addWallet() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "ADD NEW WALLET"
    }

    /**
     *  Events
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val qrCode = data?.getStringExtra("qrCode")
                walletAddressEdt.setText(qrCode!!)
            }
        }
    }

    /**
     * Call API
     */
    private fun getPaymentMethod() {
        requestHttp(UrlConstant.APP_PAYMENT_METHOD) {
            val result = it as PaymentMethodListResult
            paymentMethods = result.paymentMethods!!
            Log.e("Add wallet", paymentMethods.toString())
            spinnerWalletType.setData(paymentMethods.map { it.name })
        }
    }

    private fun addWallet() {
        val walletAddress = walletAddressEdt.text.toString()
        if (walletAddress == "") {
            Utils.showNoticeDialog(this, msg = "Please input wallet address")
            return
        }
        val methodId = paymentMethods.findLast { it.name == spinnerWalletType.getSelected() }?.id
        val params = hashMapOf("method_id" to methodId.toString(), "wallet_address" to walletAddress)

        requestHttp(UrlConstant.ADD_WALLET, params){
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    /**
     *  Navigations
     */
    fun gotoScanner() {
        val intent = Intent(this, QrScannerActivity::class.java)
        startActivityForResult(intent, 1)
    }
}