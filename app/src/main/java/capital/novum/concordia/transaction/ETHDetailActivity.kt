package capital.novum.concordia.transaction

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.WriterException
import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageView
import capital.novum.concordia.main.ProjectListActivity
import capital.novum.concordia.model.Project
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.util.Utils
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.transaction_eth_detail_activity.*


class ETHDetailActivity : BaseActivity() {
    lateinit var project: Project
    var paymentMethod = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val projectId = intent.getIntExtra("projectId", 0)
        paymentMethod = intent.getStringExtra("paymentMethod")
        val paymentAmount = intent.getStringExtra("paymentAmount")
        paymentAmountTxt.text = "$paymentAmount $paymentMethod"
        getProjectDetail(projectId)
    }
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_eth_detail_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(4)
        btnNext.setOnClickListener { goToShareInformation() }

    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
        rightToolbarButton.visibility = View.VISIBLE
        rightToolbarButton.setImageResource(R.mipmap.done_blue)
        leftToolbarButton.visibility = View.INVISIBLE
    }

    private fun setupLayout(){
        header.setProjectTitle(project.title)
        header.setProjectIcon(project.logo)
        tokenNameTxt.text = "${project.title} tokens. "
        val paymentMethod = project.paymentMethods.find{ it.methodName == paymentMethod }
        val address = paymentMethod?.walletAddress!!
        walletAddress.setText(address)
        qrCode.setImageBitmap(Utils.getQrCode(address))
    }

    /*
        Events
     */

    override fun rightToolbarClick() {
        super.rightToolbarClick()
        goToProjectList()
    }

    /**
     *  Call API
     */
    private fun getProjectDetail(projectId: Int) {
        showProgressSpinner()
        val token = PreferenceManager.getDefaultSharedPreferences(this).getString(UserConstant.token, "")
        val observer = concordiaService.getProjectDetail(token, projectId)
        disposable = observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(this, msg = result.message)
                    } else {
                        project = result.project
                        setupLayout()
                    }
                }
    }
    /**
     *  Navigations
     */

    fun goToShareInformation() {
        val intent = Intent(this, ShareInformationActivity::class.java)
        startActivity(intent)
    }

    fun goToProjectList() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }
}