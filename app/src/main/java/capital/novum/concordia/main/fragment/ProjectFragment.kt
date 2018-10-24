package capital.novum.concordia.main.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import capital.novum.concordia.R
import capital.novum.concordia.transaction.ProjectDetailActivity

class ProjectFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.project_fragment, container, false)
        view.findViewById<Button>(R.id.detail_button).setOnClickListener{goNext()}
        return view
    }

    fun goNext() {
        val intent = Intent(activity, ProjectDetailActivity::class.java)
        activity?.startActivity(intent)
    }
}