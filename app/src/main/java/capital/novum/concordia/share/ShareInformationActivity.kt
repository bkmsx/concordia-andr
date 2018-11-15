package capital.novum.concordia.share

import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class ShareInformationActivity : BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.share_information_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "SHARE WITH FRIENDS"
    }

    /**
     *  Events
     */


    /**
     *  Navigations
     */

}