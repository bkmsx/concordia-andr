package capital.novum.concordia.transaction

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.Project
import capital.novum.concordia.model.ProjectDetail
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_discount_tier.view.*
import kotlinx.android.synthetic.main.project_detail_activity.*

class ProjectDetailActivity : BaseActivity() {
    var projectId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectId = intent.getIntExtra("projectId", 0)
        getProjectDetail()
    }
    /*
        Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.project_detail_activity
    }

    override fun customViews() {
        super.customViews()

        btnParticipate.setOnClickListener { goToTermsAndCoditions() }
        btnInviteFriend.setOnClickListener { goToShareInformation() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "W GREEN PAY"
    }

    private fun setupLayout(project: Project) {
        Picasso.get().load(project.logo).into(projectImage)
        projectTitle.text = project.title.toUpperCase()
        addedDate.text = "ADDED: ${project.addedDate}"
        shortDes.text = project.shortDescription
        longDes.text = project.detailedDescription
        currentTier.text = project.currentTier
        for (salePeriod in project.salePeriods) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_discount_tier, null)
            view.period.text = "${salePeriod.title}: ${salePeriod.saleStart} - ${salePeriod.saleEnd}"
            if (salePeriod.discount != null)
                view.bonus.text = "Bonus: ${salePeriod.discount}%"
            contentView.addView(view)
        }
    }

    /*
        Events
     */

    private fun goToTermsAndCoditions() {
        val intent = Intent(this, TermsAndCoditionsActivity::class.java)
        intent.putExtra("projectId", projectId)
        startActivity(intent)
    }

    private fun goToShareInformation() {
        val intent = Intent(this, ShareInformationActivity::class.java)
        startActivity(intent)
    }

    /**
     * Call API
     */
    private fun getProjectDetail() {
        requestHttp(UrlConstant.PROJECT_DETAIL, hashMapOf("project_id" to projectId.toString())) {
            val result = it as ProjectDetail
            setupLayout(result.project!!)
        }
    }
}