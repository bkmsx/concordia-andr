package capital.novum.concordia.setting

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.transaction.QrScannerActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.setting_edit_wallet_activity.*

class EditWalletActivity : BaseActivity() {
    val walletId: String get() {return intent.getStringExtra("walletId")}
    val methodId: String get() {return intent.getStringExtra("methodId")}
    val oldAddress: String get() {return intent.getStringExtra("wallet")}

    /**
     *  Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_edit_wallet_activity
    }

    override fun customViews() {
        super.customViews()
        oldWalletEdt.setText(oldAddress)
        btnScan.setIcon(R.mipmap.blue_scan)
        btnScan.setTitle("Scan QR")
        btnScan.setOnClickListener { askCameraPermission { gotoScanner() } }
        btnNext.setOnClickListener { updateWallet() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE WALLET"
    }

    /**
     *  Events
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val qrCode = data?.getStringExtra("qrCode")
                newWalletEdt.setText(qrCode!!)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gotoScanner()
                } else {
                    Utils.showNoticeDialog(this, msg = "Scan feature needs camera permission")
                }
            }
            else -> {}
        }
    }

    /**
     *  Call API
     */
    private fun updateWallet() {
        val newWallet = newWalletEdt.text.toString()
        if (newWallet == "") {
            Utils.showNoticeDialog(this, msg = "Please input new wallet address")
            return
        }
        val params = hashMapOf(
                "method_id" to methodId,
                "wallet_id" to walletId,
                "wallet_address" to newWallet
        )
        requestHttp(UrlConstant.UPDATE_WALLET, params) {
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