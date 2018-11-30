package capital.novum.concordia.setting.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.model.ReferralBonus
import kotlinx.android.synthetic.main.item_referral_cven.view.*

class CvenBonusAdapter : RecyclerView.Adapter<CvenBonusAdapter.ViewHolder>() {
    var data: List<ReferralBonus> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
        Log.e("Adapter", "Set")
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return data.count() + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) return
        val view = holder.itemView
        val referralBonus = data.get(position - 1)
        view.nameTxt.text = referralBonus.userName
        view.tierTxt.text = "${referralBonus.tier}"
        view.pointTxt.text = referralBonus.amount
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return R.layout.item_referral_list_header
        } else {
            return R.layout.item_referral_cven
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}