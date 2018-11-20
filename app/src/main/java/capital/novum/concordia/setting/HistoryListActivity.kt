package capital.novum.concordia.setting

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.setting.adapter.ParticipateHistoryAdapter
import kotlinx.android.synthetic.main.setting_history_list_activity.*

class HistoryListActivity : BaseActivity() {
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_history_list_activity
    }

    override fun customViews() {
        super.customViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ParticipateHistoryAdapter(arrayOf("1"))
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