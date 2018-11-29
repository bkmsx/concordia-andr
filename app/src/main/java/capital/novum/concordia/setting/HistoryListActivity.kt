package capital.novum.concordia.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import capital.novum.concordia.R
import capital.novum.concordia.main.BaseActivity
import capital.novum.concordia.model.ParticipateListResult
import capital.novum.concordia.setting.adapter.ParticipateHistoryAdapter
import capital.novum.concordia.transaction.ETHDetailActivity
import capital.novum.concordia.transaction.ProjectDetailActivity
import capital.novum.concordia.transaction.USDDetailActivity
import capital.novum.concordia.util.UrlConstant
import kotlinx.android.synthetic.main.setting_history_list_activity.*

class HistoryListActivity : BaseActivity(), ParticipateHistoryAdapter.OnParticipateHistoryListener {

    lateinit var adapter: ParticipateHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHistoryList()
    }
    /*
        Custom views
     */

    override fun getLayoutId(): Int {
        return R.layout.setting_history_list_activity
    }

    override fun customViews() {
        super.customViews()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ParticipateHistoryAdapter(this)
        adapter.delegate = this
        recyclerView.adapter = adapter
    }

    override fun setupToolBar() {
        super.setupToolBar()
        toolbarTitle.text = "PARTICIPATE HISTORY"
    }

    /**
     *  Call API
     */
    private fun getHistoryList() {
        requestHttp(UrlConstant.LIST_PARTICIPATE) {
            val result = it as ParticipateListResult
            adapter.loadData(result.history)
        }
    }

    /*
        Events
     */

    override fun onDeleteItem(historyId: Int) {
        requestHttp(UrlConstant.DELETE_PARTICIPATE,
                hashMapOf("history_id" to "$historyId")) {
            getHistoryList()
        }
    }

    override fun onSelectItem(historyId: Int, method: String) {
        val intent = Intent(this, if (method == "USD") UsdParticipateDetail::class.java else EthParticipateDetail::class.java)
        intent.putExtra("historyId", historyId)
        startActivity(intent)
    }

    override fun participateAgain(projectId: Int) {
        val intent = Intent(this, ProjectDetailActivity::class.java)
        intent.putExtra("projectId", projectId)
        startActivity(intent)
    }

}