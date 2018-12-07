package capital.novum.concordia.share

import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.share_methods_activity.*
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.preference.PreferenceManager
import android.util.Log
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.setting.PhoneListActivity
import capital.novum.concordia.util.Utils


class ShareMethodsActivity : BaseActivity(){
    val refCode by lazy {PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.referralCode, null)}
    override fun getLayoutId(): Int {
        return R.layout.share_methods_activity
    }

    override fun customViews() {
        super.customViews()
        btnWhatsapp.setOnClickListener {
            Utils.blinkView(this, it)
            gotoWhatsapp()
        }
        btnSms.setOnClickListener {
            Utils.blinkView(this, it)
            gotoSms()
        }
        appLinkBtn.setOnClickListener {
            Utils.blinkView(this, it)
            Utils.copyText(this, "https://concordia.ventures")
        }
        referralCodeTxt.setText(refCode)
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "INVITE FRIENDS"
    }

    /**
     *  Navigations
     */
    private fun gotoWhatsapp() {
        val pm = packageManager
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"
            val text = "Hi there! I've just tried Concordia Ventures App and it looks interesting. " +
                    "It allows me to earn passive income for life! Sign up with this code \"$refCode\" to register for free! " +
                    "You can download from https://www.concordia.ventures"

            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp")

            waIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(waIntent, "Share with"))

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun gotoSms() {
        val intent = Intent(this, PhoneListActivity::class.java)
        startActivity(intent)
    }
}