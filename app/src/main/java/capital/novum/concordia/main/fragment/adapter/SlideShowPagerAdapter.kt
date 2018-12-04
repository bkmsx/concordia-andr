package capital.novum.concordia.main.fragment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import capital.novum.concordia.main.fragment.SlideFragment

class SlideShowPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val fragments = List(5) {
        val fragment = SlideFragment()
        fragment.position = it
        fragment
    }
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return 5
    }

}