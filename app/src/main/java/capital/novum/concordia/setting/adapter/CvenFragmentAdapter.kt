package capital.novum.concordia.setting.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import capital.novum.concordia.setting.fragment.CvenBuyFragment
import capital.novum.concordia.setting.fragment.CvenSellFragment

class CvenFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val cvenBuyFragment by lazy { CvenBuyFragment() }
    val cvenSellFragment by lazy { CvenSellFragment() }
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return cvenBuyFragment
        } else {
            return cvenSellFragment
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
}