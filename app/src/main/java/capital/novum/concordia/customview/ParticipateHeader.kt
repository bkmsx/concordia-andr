package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.util.LayoutDirection
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R
import kotlinx.android.synthetic.main.participate_header.view.*
import java.util.jar.Attributes

class ParticipateHeader : LinearLayout{

    constructor(context: Context, attributes: AttributeSet): super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.participate_header, this, true)
        this.setBackgroundResource(R.mipmap.pattern)
        setIndex(1)
    }

    fun setIndex(index: Int) {
        for (i in 0..(indicator.childCount - 1)) {
            if (i < index) {
                indicator.getChildAt(i).setBackgroundResource(R.drawable.participate_indicator_blue)
            } else {
                indicator.getChildAt(i).setBackgroundResource(R.drawable.participate_indicator_white)
            }
         }
    }
}