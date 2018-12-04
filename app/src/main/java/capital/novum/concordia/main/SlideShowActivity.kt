package capital.novum.concordia.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import capital.novum.concordia.R
import capital.novum.concordia.main.fragment.adapter.SlideShowPagerAdapter
import kotlinx.android.synthetic.main.slide_show_activity.*

class SlideShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide_show_activity)
        viewpager.adapter = SlideShowPagerAdapter(supportFragmentManager)
    }
}