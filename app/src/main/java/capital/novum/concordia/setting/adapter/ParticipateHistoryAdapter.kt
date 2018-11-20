package capital.novum.concordia.setting.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R

class ParticipateHistoryAdapter : RecyclerView.Adapter<ParticipateHistoryAdapter.ViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_participate_history, parent, false))
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    constructor(data: Array<String>)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}