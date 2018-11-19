package capital.novum.concordia.setting.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R

class WalletAdapter : RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    constructor(data: Array<String>): super() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
       return 3
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return R.layout.item_wallet_header
        } else {
            return R.layout.item_wallet_body
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 2) {
            holder.itemView.setBackgroundResource(R.drawable.wallet_footer_bg)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}