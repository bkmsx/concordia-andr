package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.participate_header.view.*

class ParticipateHeader : LinearLayout{

    constructor(context: Context, attributes: AttributeSet): super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.participate_header, this, true)
        this.setBackgroundResource(R.mipmap.pattern)
        setIndex(1)
    }

    fun setIndex(index: Int) {
        for (i in 0..(indicator.childCount - 1)) {
            if (i < index) {
                indicator.getChildAt(i).setBackgroundResource(R.drawable.light_blue_circle_bg)
            } else {
                indicator.getChildAt(i).setBackgroundResource(R.drawable.white_circle_bg)
            }
         }
    }

    fun setProjectIcon(link: String) {
        Picasso.get().load(link).into(projectIconImg)
    }

    fun setProjectTitle(title: String) {
        projectTitle.text = title.toUpperCase()
    }
}