package capital.novum.concordia.setting

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.setting.adapter.WalletAdapter
import kotlinx.android.synthetic.main.setting_wallet_list_activity.*
import android.provider.ContactsContract
import android.content.ContentResolver
import android.util.Log
import android.widget.Toast




class PhoneListActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_phone_list_activity
    }

    override fun customViews() {
        super.customViews()
        getContactList()
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "SHARE WITH FRIENDS"
    }

    /*
        Events
     */
    private fun getContactList() {
        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            Log.e("Phone", "Name: $name, Number: $phoneNumber")

        }
        phones.close()
    }


    /**
     *  Navigations
     */

}