package capital.novum.concordia.setting

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import android.provider.ContactsContract
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.net.Uri
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import capital.novum.concordia.model.PhoneNumber
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.setting.adapter.PhoneListAdapter
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.setting_phone_list_activity.*


class PhoneListActivity : BaseActivity(), PhoneListAdapter.PhoneListAdapterDelegate {
    lateinit var adapter: PhoneListAdapter
    var phoneList = ArrayList<PhoneNumber>()

    /**
     *  Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_phone_list_activity
    }

    override fun customViews() {
        super.customViews()
        adapter = PhoneListAdapter()
        adapter.delegate = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        askContactPermission { getContactList() }
        searchEdt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                adapter.data = phoneList.filter { it.name.contains(s.toString(), true) }
            }
        })
        btnNext.setOnClickListener { gotoSendMsg() }
    }



    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "SHARE WITH FRIENDS"
    }

    /**
     *  Events
     */
    private fun getContactList() {
        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            if (!name.startsWith("+") && !phoneList.map { it.number }.contains(phoneNumber))
                phoneList.add(PhoneNumber(name, phoneNumber))
        }
        phones.close()
        phoneList.sortBy { it.name }
        adapter.data = phoneList
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1001 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList()
                } else {
                    Utils.showNoticeDialog(this, msg = "Contacts list needs contacts permission")
                }
            }
            else -> {}
        }
    }

    override fun onSelectedNumberChanged(total: Int) {
        totalTxt.text = total.toString()
    }

    /**
     *  Navigations
     */
    private fun gotoSendMsg() {
        var number = ""
        adapter.selectedData.forEach { number += "$it," }
        val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number"))
        val refCode = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.referralCode, "")
        val msg = "Hi there! I've just tried Concordia Ventures App and it looks interesting. " +
                "It allows me to earn passive income for life! Sign up with this code \"$refCode\" to register for free! " +
                "You can download from https://www.concordia.ventures"
        smsIntent.putExtra("sms_body", msg)
        startActivity(smsIntent)
    }
}