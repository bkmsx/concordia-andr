package capital.novum.concordia.share

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.PromotionModel
import capital.novum.concordia.model.PromotionResult
import capital.novum.concordia.util.UrlConstant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.share_information_activity.*

class ShareInformationActivity : BaseActivity(){
    val projectId  by lazy { intent.getIntExtra("projectId", 0) }
    /**
     *  Custom views
     */
    override fun getLayoutId(): Int {
        return R.layout.share_information_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "SHARE WITH FRIENDS"
        btnNext.setOnClickListener { goToShareMethods() }
    }

    override fun customViews() {
        super.customViews()
        getPromotionInformation()
    }

    private fun setupLayout(promotion: PromotionModel) {
        titleTxt.text = promotion.title
        subtitleTxt.text = promotion.subTitle
        Picasso.get().load(promotion.bannerImage).into(bannerImg)
        captionTxt.text = promotion.caption
        shortDesTxt.text = promotion.shortDescription
        detailDesTxt.text = promotion.detailedDescription
        example1Txt.text = promotion.exampleDescription
        example2Txt.visibility = View.GONE
        example3Txt.visibility = View.GONE
        example4Txt.visibility = View.GONE
        example5Txt.visibility = View.GONE
        example6Txt.visibility = View.GONE
        example7Txt.visibility = View.GONE
    }

    /**
     *  Call API
     */
    fun getPromotionInformation() {
        if (projectId == 0) return
        requestHttp(UrlConstant.PROJECT_SHARE, hashMapOf("project_id" to projectId.toString())) {
            val result = it as PromotionResult
            setupLayout(result.promotion!!)
        }
    }

    /**
     *  Navigations
     */
    private fun goToShareMethods() {
        val intent = Intent(this, ShareMethodsActivity::class.java)
        startActivity(intent)
    }
}