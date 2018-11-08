package capital.novum.concordia.main.fragment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import capital.novum.concordia.R
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectAdapter: RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    constructor()

    override fun getItemCount(): Int {
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ProjectAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView("Tran Van Sieu")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(name : String) {
            itemView.textview.text = name
        }
    }
}
