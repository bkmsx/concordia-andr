package capital.novum.concordia.transaction

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.customview.EditSpinner
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.*
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.transaction_input_wallet_activity.*
import kotlinx.android.synthetic.main.edit_spinner.view.*


class InputWalletActivity : BaseActivity(), EditSpinner.OnEditSpinnerChanged {
    private val tag = javaClass.toString()
    var projectId = 0
    var paymentMethods = ArrayList<String>()
    lateinit var userWallets: List<UserWalletCategory>
    lateinit var project: Project
    lateinit var selectedMethod: PaymentMethod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectId = intent.getIntExtra("projectId", 0)
        getProjectDetail()
    }
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
        registerKeyboardListener()
        paymentSpinner.preventEdit()
        paymentSpinner.changeBackground(R.drawable.blue_bottom_line_bg)
        paymentSpinner.changeTextColor(Color.WHITE)
        paymentSpinner.delegate = this
        btnNext.setOnClickListener {
            checkWalletAddress()
        }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    private fun checkWalletAddress() {
        selectedMethod = project.paymentMethods.filter { it.methodName == paymentSpinner.getSelected() }.first()
        if (selectedMethod.methodName == "USD") {
            goToAmountTokens()
            return
        }
        val walletCategory = userWallets.filter { it.methodName == paymentSpinner.getSelected() }.firstOrNull()
        if (walletCategory != null && !walletCategory.wallets.none { it.address == walletSpinner.getSelected() }) {
                goToAmountTokens()
                return
        }
        addWallet(selectedMethod.methodId, walletSpinner.getSelected())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val qrCode = data?.getStringExtra("qrCode")
                walletSpinner.setText(qrCode!!)
            }
        }
    }

    private fun filterUserWallet() {
        val walletCategories = userWallets.filter { it.methodName == paymentSpinner.getSelected() }
        val wallets = ArrayList<String>()
        if (!walletCategories.isEmpty()) {
            for (wallet in walletCategories.first().wallets) {
                wallets.add(wallet.address)
            }
        }
        walletSpinner.setData(wallets)
    }

    override fun onEditSpinnerChanged(selectedItem: String) {
        walletSpinner.setText("")
        filterUserWallet()
    }

    /**
     *  Call API
     */
    private fun addWallet(methodId: Int, walletAddress: String) {
        requestHttp(UrlConstant.ADD_WALLET, hashMapOf("method_id" to methodId.toString(), "wallet_address" to walletAddress)) {
            goToAmountTokens()
        }
    }

    private fun getProjectDetail() {
        requestHttp(UrlConstant.PROJECT_DETAIL, hashMapOf("project_id" to projectId.toString())) {
            val result = it as ProjectDetail
            project = result.project!!
            header.setProjectIcon(project.logo)
            header.setProjectTitle(project.title)
            for (method in project.paymentMethods) {
                paymentMethods.add(method.methodName)
            }
            paymentSpinner.setData(paymentMethods)
            getUserWallet()
        }
    }

    private fun getUserWallet() {
        requestHttp(UrlConstant.LIST_WALLET) {
            val result = it as UserWallets
            userWallets = result.wallets
            filterUserWallet()
        }
    }
    /**
     *  Navigations
     */

    private fun goToAmountTokens() {
        val intent = Intent(this, AmountTokensActivity::class.java)
        intent.putExtra("projectId", projectId)
        intent.putExtra("projectLink", project.logo)
        intent.putExtra("projectName", project.title)
        intent.putExtra("paymentMethod", selectedMethod.methodName)
        intent.putExtra("paymentMethodId", selectedMethod.methodId)
        intent.putExtra("tokenPrice", selectedMethod.pricePerToken)
        intent.putExtra("walletAddress", walletSpinner.getSelected())
        intent.putExtra("discount", project.currentDiscount ?: "0")
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
                walletSpinner.edittext.clearFocus()
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