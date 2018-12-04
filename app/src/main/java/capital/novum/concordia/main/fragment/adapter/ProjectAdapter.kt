package capital.novum.concordia.main.fragment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.model.Project
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectAdapter: RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {
    lateinit var onProjectListener: OnProjectListener
    var data: List<Project>? = null

    override fun getItemCount(): Int {
        return if (data == null) 0 else (data as List<Project>).count()
    }

    fun loadData(data: List<Project>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ProjectAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = data?.get(position)
        val view = holder.itemView
        view.projectTitle.text = project?.title?.toUpperCase()
        Picasso.get().load(project?.logo).into(view.projectIconImg)
        view.addedDate.text = "ADDED: ${project?.addedDate}"
        view.shortDes.text = project?.shortDescription
        view.saleEndTxt.visibility = if (project?.status == "sale_ended") View.VISIBLE else View.INVISIBLE
        view.participateImg.visibility = if (project?.userParticipated == "true") View.VISIBLE else View.INVISIBLE
        if (project?.currentDiscount != null) {
            view.bonus.text = "${project?.currentDiscount}%"
        }
        view.button.setOnClickListener {
            onProjectListener.onDetailButtonClick(project?.projectId)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnProjectListener {
        fun onDetailButtonClick(projectId: Int?)
    }
}
