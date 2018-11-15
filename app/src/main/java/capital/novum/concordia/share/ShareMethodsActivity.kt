package capital.novum.concordia.share

import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class ShareMethodsActivity : BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.share_methods_activity
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

}