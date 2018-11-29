package capital.novum.concordia.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import capital.novum.concordia.R
import kotlinx.android.synthetic.main.test_item.view.*

class SpinnerAdapter : BaseAdapter {
    var context: Context
    var data: List<String>
    constructor(context: Context, data: List<String>) : super() {
        this.context = context
        this.data = data
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        var view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_item, null)
            holder = ViewHolder()
            holder.textView = view.textView
            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }
        holder.textView?.text = data[position]
       return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        if (data != null) {
            return data.count()
        }
        return 0
    }

    internal class ViewHolder {
        var textView: TextView? = null
    }
}