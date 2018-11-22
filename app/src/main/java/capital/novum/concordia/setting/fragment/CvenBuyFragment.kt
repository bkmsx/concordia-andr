package capital.novum.concordia.setting.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capital.novum.concordia.R
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.cven_buy_fragment.view.*
import kotlinx.android.synthetic.main.dialog_cven_exchange.*

class CvenBuyFragment : Fragment() {
    val CVEN_ADDRESS = "0x688dde13bD594A9030feeFe6fa39cb353B7351c7"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.cven_buy_fragment, container, false)
        view.cvenAddress.setText(CVEN_ADDRESS)
        view.qrCode.setImageBitmap(Utils.getQrCode(CVEN_ADDRESS))
        view.btnDialog.setOnClickListener { Utils.showDialog(context) }
        return view
    }


}