package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R
import kotlinx.android.synthetic.main.copy_textview.view.*

class CopyTextView : LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.copy_textview, this, true)
        setBackgroundResource(R.drawable.blue2_stroke_round_bg20)
    }

    fun setText(text: String) {
        textView.text = text
    }
}