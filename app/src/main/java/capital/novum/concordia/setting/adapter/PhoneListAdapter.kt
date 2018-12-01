package capital.novum.concordia.setting.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import capital.novum.concordia.R
import capital.novum.concordia.model.PhoneNumber
import kotlinx.android.synthetic.main.item_phone_list.view.*

class PhoneListAdapter : RecyclerView.Adapter<PhoneListAdapter.ViewHolder>() {
    var delegate: PhoneListAdapterDelegate? = null
    var selectedData = HashSet<String>()
    var data: List<PhoneNumber> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_phone_list, parent, false))
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val item = data[position]
        view.phoneNumberTxt.text = item.name
        view.checkbox.isChecked = item.number in selectedData

        view.checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (buttonView.isShown) {
                if (isChecked) {
                    selectedData.add(item.number)
                } else {
                    selectedData.remove(item.number)
                }
                delegate?.onSelectedNumberChanged(selectedData.size)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface PhoneListAdapterDelegate {
        fun onSelectedNumberChanged(total: Int)
    }
}