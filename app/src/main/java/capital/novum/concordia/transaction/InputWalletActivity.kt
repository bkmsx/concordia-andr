package capital.novum.concordia.transaction

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.transaction_input_wallet_activity.*
import kotlinx.android.synthetic.main.edit_spinner.view.*


class InputWalletActivity : BaseActivity() {
    private val tag = javaClass.toString()
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_input_wallet_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(2)
        btnScan.setOnClickListener { gotoScanner() }
        btnScan.setTitle("SCAN")
        btnScan.setIcon(R.mipmap.blue_scan)
        editSpinner.setData(arrayOf("1", "2", "3"))
        registerKeyboardListener()
        paymentSpinner.preventEdit()
        paymentSpinner.setData(arrayOf("ETH", "USD", "XLM"))
        paymentSpinner.changeBackground(R.drawable.blue_bottom_line_bg)
        paymentSpinner.changeTextColor(Color.WHITE)

        btnNext.setOnClickListener { goToAmountTokens() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    fun goNext(view : View) {
        val intent = Intent(this, AmountTokensActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val qrCode = data?.getStringExtra("qrCode")
                editSpinner.setText(qrCode!!)
            }
        }
    }

    /**
     *  Navigations
     */

    private fun goToAmountTokens() {
        val intent = Intent(this, AmountTokensActivity::class.java)
        startActivity(intent)
    }

    fun gotoScanner() {
        val intent = Intent(this, QrScannerActivity::class.java)
        startActivityForResult(intent, 1)
    }

    var isOpen = false
    fun registerKeyboardListener() {
        mainLayout.viewTreeObserver.addOnGlobalLayoutListener {
            if (keyboardShown(mainLayout.rootView)) {
                isOpen = true
            } else if (isOpen){
                editSpinner.edittext.clearFocus()
                isOpen = false
            }
        }
    }

    private fun keyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }
}