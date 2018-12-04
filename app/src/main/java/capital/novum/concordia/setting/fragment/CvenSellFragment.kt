package capital.novum.concordia.setting.fragment

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.cven_sell_fragment.view.*

class CvenSellFragment : Fragment() {
    val CVEN_ADDRESS = "0x688dde13bD594A9030feeFe6fa39cb353B7351c7"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.cven_sell_fragment, container, false)
        view.cvenAddress.setText(CVEN_ADDRESS)
        view.btnDialog.setOnClickListener { Utils.showCvenDialog(context) }
        return view
    }

    class doAsync(val callback: (Bitmap) -> Unit) : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg params: String?): Bitmap {
            return Utils.getQrCode(params[0]!!)
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            callback(result!!)
        }
    }
}