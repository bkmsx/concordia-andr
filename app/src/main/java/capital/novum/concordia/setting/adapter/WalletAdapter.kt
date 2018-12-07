package capital.novum.concordia.setting.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.model.DisplayType
import capital.novum.concordia.model.DisplayWalletModel
import capital.novum.concordia.model.UserWalletCategory
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.item_wallet_body.view.*

class WalletAdapter : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {
    var delegate : WalletAdapterDelegate? = null
    var listWallet: ArrayList<DisplayWalletModel> = ArrayList()
    var data: List<UserWalletCategory> = listOf()
    set(value) {
        listWallet.clear()
        value.forEach {
            listWallet.add(DisplayWalletModel(0, 0, it.methodName, DisplayType.HEADER))
            it.wallets.forEach {
                listWallet.add(DisplayWalletModel(it.walletId, it.methodId, it.address, DisplayType.BODY))
            }
            listWallet.last().type = DisplayType.FOOTER
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
       return listWallet.count()
    }

    override fun getItemViewType(position: Int): Int {
        if (listWallet.get(position).type == DisplayType.HEADER) {
            return R.layout.item_wallet_header
        } else {
            return R.layout.item_wallet_body
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val displayWalletModel = listWallet.get(position)
        view.nameTxt.text = displayWalletModel.name
        if (displayWalletModel.type != DisplayType.HEADER) {
            view.deleteBtn.setOnClickListener {
                Utils.blinkView(it.context, view.deleteBtn)
                delegate?.deleteWallet(displayWalletModel.id, displayWalletModel.name)
            }
            view.editBtn.setOnClickListener {
                Utils.blinkView(it.context, it)
                delegate?.editWallet(displayWalletModel.id, displayWalletModel.methodId, displayWalletModel.name)
            }
        }
        if (displayWalletModel.type == DisplayType.FOOTER) {
            holder.itemView.setBackgroundResource(R.drawable.blur_gray_bottom_round_bg)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface WalletAdapterDelegate {
        fun deleteWallet(walletId: Int, walletAddress: String)
        fun editWallet(walletId: Int, methodId: Int, wallet: String)
    }
}