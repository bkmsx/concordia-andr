package capital.novum.concordia.setting.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import capital.novum.concordia.setting.fragment.CvenBuyFragment
import capital.novum.concordia.setting.fragment.CvenSellFragment

class CvenFragmentAdapter : FragmentPagerAdapter {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return CvenBuyFragment()
        } else {
            return CvenSellFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return "BUY"
        } else {
            return "SELL"
        }
    }

    constructor(context: Context, fragmentManager: FragmentManager) : super(fragmentManager)
}