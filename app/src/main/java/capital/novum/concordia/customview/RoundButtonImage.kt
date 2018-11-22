package capital.novum.concordia.customview

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R
import kotlinx.android.synthetic.main.round_button_image.view.*

class RoundButtonImage : LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.round_button_image, this, true)
        gravity = Gravity.CENTER
        setBackgroundResource(R.drawable.white_stroke_round_res_bg)
    }

    fun setIcon(imageSrc: Int) {
        icon.setImageResource(imageSrc)
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun setBitmap(bitmap: Bitmap) {
        icon.setImageBitmap(bitmap)
    }

}