package capital.novum.concordia.main.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import capital.novum.concordia.R
import capital.novum.concordia.main.ProjectListActivity
import capital.novum.concordia.main.fragment.adapter.ProjectAdapter
import capital.novum.concordia.model.LocalData
import capital.novum.concordia.model.UserConstant
import capital.novum.concordia.transaction.ProjectDetailActivity
import capital.novum.concordia.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.project_fragment.view.*

class ProjectFragment : Fragment(), ProjectAdapter.OnProjectListener {
    lateinit var projectListActivity: ProjectListActivity
    lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectListActivity = activity as ProjectListActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.project_fragment, container, false)
        view.recyclerview.layoutManager = LinearLayoutManager(context)
        adapter = ProjectAdapter()
        adapter.onProjectListener = this
        view.recyclerview.adapter = adapter
        getProjectList()
        return view
    }

    /**
     *  Events
     */
    override fun onDetailButtonClick(projectId: Int?) {
        goNext(projectId)
    }

    /**
     *  Call API
     */
    private fun getProjectList() {
        projectListActivity.showProgressSpinner()
        val token = PreferenceManager.getDefaultSharedPreferences(projectListActivity).getString(UserConstant.token, "")
        val observer = projectListActivity.concordiaService.getProjectList(token)
        projectListActivity.disposable = observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    projectListActivity.hideProgressSpinner()
                    if (result.code != 200) {
                        Utils.showNoticeDialog(projectListActivity, msg = result.message)
                    } else {
                        adapter.loadData(result.projects)
                    }
                }
    }

    /**
     *  Navigations
     */
    fun goNext(projectId: Int?) {
        val intent = Intent(activity, ProjectDetailActivity::class.java)
        intent.putExtra("projectId", projectId)
        activity?.startActivity(intent)
    }


}