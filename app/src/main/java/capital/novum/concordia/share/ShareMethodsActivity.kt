package capital.novum.concordia.share

import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import kotlinx.android.synthetic.main.share_methods_activity.*
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import capital.novum.concordia.setting.PhoneListActivity


class ShareMethodsActivity : BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.share_methods_activity
    }

    override fun customViews() {
        super.customViews()
        btnWhatsapp.setOnClickListener { gotoWhatsapp() }
        btnSms.setOnClickListener { gotoSms() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "INVITE FRIENDS"
    }

    /**
     *  Events
     */


    /**
     *  Navigations
     */
    fun gotoWhatsapp() {
        val pm = packageManager
        try {

            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"
            val text = "YOUR TEXT HERE"

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

    fun gotoSms() {
        val intent = Intent(this, PhoneListActivity::class.java)
        startActivity(intent)
    }
}