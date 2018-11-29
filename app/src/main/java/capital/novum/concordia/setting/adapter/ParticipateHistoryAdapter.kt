package capital.novum.concordia.setting.adapter

import android.content.Context
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.model.ParticipateHistory
import capital.novum.concordia.util.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_participate_history.view.*

class ParticipateHistoryAdapter: RecyclerView.Adapter<ParticipateHistoryAdapter.ViewHolder> {
    var data: List<ParticipateHistory>? = null
    var context: Context? = null
    var delegate: OnParticipateHistoryListener? = null

    constructor(context: Context):super() {
        this.context = context
    }

    fun loadData(data: List<ParticipateHistory>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_participate_history, parent, false))
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else (data as List<ParticipateHistory>).count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val history = data?.get(position)
        Picasso.get().load(history?.logo).into(view.projectIconImg)
        view.titleTxt.text = history?.title?.toUpperCase()
        view.addedDateTxt.text = history?.addedDate
        view.tokenAmountTxt.text = "${history?.tokensPurchased} tokens purchased"
        view.paymentAmountTxt.text = "${history?.amount} ${history?.paymentMode} paid"
        view.discountTxt.text = "${history?.discount} Discount"
        view.statusTxt.text = if(history?.paymentStatus == "pedding") "PENDING PAYMENT" else "COMPLETED PAYMENT"
        view.btnClose.setOnClickListener {
            Utils.showConfirmDeleteHistoryDialog(context) {
                delegate?.onDeleteItem(history?.historyId!!)
            }
        }
        view.btnParticipate.setOnClickListener { delegate?.participateAgain(history?.projectId!!) }
        view.btnDetail.setOnClickListener { delegate?.onSelectItem(history?.historyId!!, history.paymentMode) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnParticipateHistoryListener {
        fun onDeleteItem(historyId: Int)
        fun onSelectItem(historyId: Int, method: String)
        fun participateAgain(projectId: Int)
    }
}