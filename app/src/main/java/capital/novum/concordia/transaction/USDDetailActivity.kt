package capital.novum.concordia.transaction

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.main.LoginActivity
import capital.novum.concordia.main.ProjectListActivity
import capital.novum.concordia.model.Project
import capital.novum.concordia.model.ProjectDetail
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.share.ShareMethodsActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.transaction_usd_detail_activity.*

class USDDetailActivity : BaseActivity() {
    lateinit var project: Project
    var paymentMethod = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val projectId = intent.getIntExtra("projectId", 0)
        paymentMethod = intent.getStringExtra("paymentMethod")
        val paymentAmount = intent.getStringExtra("paymentAmount")
        amoutTxt.text = "$paymentAmount $paymentMethod"
        getProjectDetail(projectId)
    }

    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_usd_detail_activity
    }

    override fun customViews() {
        super.customViews()
        header.setIndex(4)
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
        rightToolbarButton.visibility = View.VISIBLE
        rightToolbarButton.setImageResource(R.mipmap.done_blue)
        leftToolbarButton.visibility = View.INVISIBLE
        btnNext.setOnClickListener { goToShareInformation() }
    }

    private fun setupLayout() {
        header.setProjectIcon(project.logo)
        header.setProjectTitle(project.title)
        tokenNameTxt.text = "${project.title} tokens. "
        val paymentMethod = project.paymentMethods.find { it.methodName == paymentMethod }
        bankNameTxt.text = paymentMethod?.bankName
        swiftCodeTxt.text = paymentMethod?.swiftCode
        bankAddressTxt.text = paymentMethod?.bankAddress
        accountNameTxt.text = paymentMethod?.accountName
        accountNumberTxt.text = paymentMethod?.accountNumber
        businessAddressTxt.text = paymentMethod?.holderAddress
    }

    /**
     *  Events
     */
    override fun rightToolbarClick() {
        super.rightToolbarClick()
        Utils.blinkView(this, rightToolbarButton)
        goToProjectList()
    }

    /**
     *  Call API
     */
    private fun getProjectDetail(projectId: Int) {
        requestHttp(UrlConstant.PROJECT_DETAIL, hashMapOf("project_id" to projectId.toString())) {
            val result = it as ProjectDetail
            project = result.project!!
            setupLayout()
        }
    }

    /**
     *  Navigations
     */
    fun goToShareInformation() {
        val intent = Intent(this, if (project.promotion == 0)
            ShareMethodsActivity::class.java else ShareInformationActivity::class.java).apply {
            putExtra("projectId", project.projectId)
        }
        startActivity(intent)
    }

    fun goToProjectList() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }
}