package capital.novum.concordia.setting.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R

class CvenBonusAdapter : RecyclerView.Adapter<CvenBonusAdapter.ViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return 14
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return R.layout.item_referral_list_header
        } else {
            return R.layout.item_referral_cven
        }
    }

    constructor(data: Array<String>) : super()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}