package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R
import capital.novum.concordia.util.Utils
import kotlinx.android.synthetic.main.copy_textview.view.*

class CopyTextView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    init {
        LayoutInflater.from(context).inflate(R.layout.copy_textview, this, true)
        setBackgroundResource(R.drawable.blue2_stroke_round_bg20)
        imageButton.setOnClickListener {
            Utils.blinkView(context, it)
            Utils.copyText(context, textView.text.toString())
        }
    }

    fun setText(text: String) {
        textView.text = text
    }


}