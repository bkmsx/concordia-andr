package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R

class RoundButtonImage : LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.round_button_image, this, true)
        gravity = Gravity.CENTER
        setBackgroundResource(R.drawable.round_button_image_bg)
        setOnClickListener{ Log.e(javaClass.toString(), "Check") }
    }
}