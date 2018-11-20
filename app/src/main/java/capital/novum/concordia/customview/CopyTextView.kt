package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R

class CopyTextView : LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.copy_textview, this, true)
        setBackgroundResource(R.drawable.light_blue_stroke_round_bg)
    }
}