package capital.novum.concordia.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.UserWallets
import capital.novum.concordia.setting.adapter.WalletAdapter
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.setting_wallet_list_activity.*

class WalletListActivity : BaseActivity(), WalletAdapter.WalletAdapterDelegate {
    lateinit var adapter: WalletAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWalletList()
    }
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_wallet_list_activity
    }

    override fun customViews() {
        super.customViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WalletAdapter()
        adapter.delegate = this
        recyclerView.adapter = adapter

        btnNext.setOnClickListener { gotoAddWallet() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "UPDATE WALLETS"
    }

    /**
     *  Events
     */
    override fun deleteWallet(walletId: Int, walletAddress: String) {
        Utils.showConfirmDialog(this, "Are you sure you want to delete \"$walletAddress\" ?"){
            callDeleteWallet(walletId)
        }
    }

    override fun editWallet(walletId: Int, methodId: Int, wallet: String) {
        gotoEditWallet(walletId, methodId, wallet)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode in listOf(1, 2) && resultCode == Activity.RESULT_OK) {
            getWalletList()
        }
    }

    /**
     *  Call API
     */
    private fun getWalletList() {
        requestHttp(UrlConstant.LIST_WALLET) {
            val result = it as UserWallets
            adapter.data = result.wallets
        }
    }

    private fun callDeleteWallet(walletId: Int) {
        requestHttp(UrlConstant.DELETE_WALLET, hashMapOf("wallet_id" to walletId.toString())){
            getWalletList()
        }
    }

    /**
     *  Navigations
     */
    fun gotoAddWallet() {
        val intent = Intent(this, AddWalletActivity::class.java)
        startActivityForResult(intent, 2)
    }

    fun gotoEditWallet(walletId: Int, methodId: Int, wallet: String) {
        val intent = Intent(this, EditWalletActivity::class.java)
        intent.putExtra("walletId", walletId.toString())
        intent.putExtra("methodId", methodId.toString())
        intent.putExtra("wallet", wallet)
        startActivityForResult(intent, 1)
    }
}