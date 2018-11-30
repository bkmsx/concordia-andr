package capital.novum.concordia.transaction

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.ProjectDetail
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.transaction_terms_conditions_activity.*

class TermsAndCoditionsActivity : BaseActivity() {
    var projectId = 0
    var projectUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectId = intent.getIntExtra("projectId", 0)
        getProjectDetail()
    }
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.transaction_terms_conditions_activity
    }

    override fun customViews() {
        super.customViews()
        btnNext.setOnClickListener { goNext() }
        btnDialog.setOnClickListener { Utils.showTermConditions(this, projectUrl) }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE"
    }

    /*
        Events
     */

    fun goNext() {
        val intent = Intent(this, InputWalletActivity::class.java)
        intent.putExtra("projectId", projectId)
        startActivity(intent)
    }

    /**
     *  Call API
     */
    private fun getProjectDetail() {
        requestHttp(UrlConstant.PROJECT_DETAIL, hashMapOf("project_id" to projectId.toString())) {
            val result = it as ProjectDetail
            val project = result.project!!
            projectUrl = project.termsUrl
            header.setProjectIcon(project.logo)
            header.setProjectTitle(project.title)
        }
    }
}