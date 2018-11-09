package capital.novum.concordia.main.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import capital.novum.concordia.R
import capital.novum.concordia.main.fragment.adapter.ProjectAdapter
import capital.novum.concordia.transaction.ProjectDetailActivity
import kotlinx.android.synthetic.main.project_fragment.view.*

class ProjectFragment : Fragment(), ProjectAdapter.OnProjectListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.project_fragment, container, false)
        view.recyclerview.layoutManager = LinearLayoutManager(context)
        var adapter = ProjectAdapter()
        adapter.onProjectListener = this
        view.recyclerview.adapter = adapter
        return view
    }

    /**
     *  Events
     */
    override fun onDetailButtonClick() {
        goNext()
    }

    /**
     *  Navigations
     */
    fun goNext() {
        val intent = Intent(activity, ProjectDetailActivity::class.java)
        activity?.startActivity(intent)
    }


}