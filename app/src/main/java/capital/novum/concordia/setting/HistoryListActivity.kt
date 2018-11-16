package capital.novum.concordia.setting

import android.content.Intent
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity

class HistoryListActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_history_list_activity
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE HISTORY"
    }

    /*
        Events
     */

    fun goNext(view : View) {

    }
}