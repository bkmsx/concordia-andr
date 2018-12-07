package capital.novum.concordia.transaction

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.Project
import capital.novum.concordia.model.ProjectDetail
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.share.ShareInformationActivity
import capital.novum.concordia.share.ShareMethodsActivity
import capital.novum.concordia.util.Constants
import capital.novum.concordia.util.UrlConstant
import capital.novum.concordia.util.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_discount_tier.view.*
import kotlinx.android.synthetic.main.project_detail_activity.*

class ProjectDetailActivity : BaseActivity() {
    var projectId = 0
    lateinit var project: Project
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
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val status = sharedPreferences.getString(UserConstant.status, null)
        if (status != Constants.CLEARED) {
            statusImg.setImageResource(R.mipmap.timer)
            statusTxt.text = "You are unverified"
        }

        btnParticipate.setOnClickListener {
            if (status != Constants.CLEARED) {
                Utils.showNoticeDialog(this, msg = getString(R.string.project_detail_notice_unverified))
                return@setOnClickListener
            }
            goToTermsAndCoditions()
        }
        btnInviteFriend.setOnClickListener { goToShareInformation() }
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "DETAIL"
    }

    private fun setupLayout(project: Project) {
        Picasso.get().load(project.logo).into(projectImage)
        projectTitle.text = project.title.toUpperCase()
        addedDate.text = "ADDED: ${project.addedDate}"
        shortDes.text = project.shortDescription
        longDes.text = project.detailedDescription
        if (project.status == "sale_ended") {
            btnParticipate.isEnabled = false
            btnParticipate.setBackgroundResource(R.drawable.gray_round_bg)
            currentTier.text = "Sale Ended"
        } else {
            currentTier.text = project.currentTier
        }
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
        val intent = Intent(this, TermsAndConditionsActivity::class.java)
        intent.putExtra("projectId", projectId)
        startActivity(intent)
    }

    private fun goToShareInformation() {
        val intent = Intent(this, if(project.promotion == 0)
            ShareMethodsActivity::class.java else ShareInformationActivity::class.java)
        startActivity(intent)
    }

    /**
     * Call API
     */
    private fun getProjectDetail() {
        requestHttp(UrlConstant.PROJECT_DETAIL, hashMapOf("project_id" to projectId.toString())) {
            val result = it as ProjectDetail
            project = result.project!!
            setupLayout(project)
        }
    }
}